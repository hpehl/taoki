package name.pehl.gwt.taoki.client.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.URL;

/**
 * Klasse zum Erstellen von Resourcen-URLs in Esoves. Eine Resourcen-URL in
 * Esoves setzt sich aus den folgenden Bestandteilen zusammen:<br/>
 * <code>&lt;protocol&gt;://&lt;host&gt;[:&lt;port&gt;]&lt;context&gt;/&lt;module&gt;-&lt;version&gt;/&lt;resourcePaths&gt;[?&lt;queryParameter&gt;]</code>
 * <p>
 * <table>
 * <tr>
 * <th>Bestandteil</th>
 * <th>Beschreibung</th>
 * <th>Defaultwert</th>
 * <th>Beispiele</th>
 * </tr>
 * <tr>
 * <td>protocol</td>
 * <td>Das Protokoll. Falls angegeben, darf der Wert nicht null/leer sein.</td>
 * <td>http</td>
 * <td>http, https</td>
 * </tr>
 * <tr>
 * <td>host</td>
 * <td>Der Hostname. Kann auch einen Port beinhalten. Falls angegeben, darf der
 * Wert nicht null/leer sein.</td>
 * <td>localhost</td>
 * <td>127.0.0.1, www.esoves.de:8080</td>
 * </tr>
 * <tr>
 * <td>port</td>
 * <td>Die Portnummer. Falls angegeben muss der Wert positiv sein</td>
 * <td>keiner</td>
 * <td>80, 8080</td>
 * </tr>
 * <tr>
 * <td>context</td>
 * <td>Der Context-Pfad. Falls angegeben, darf der Wert nicht null/leer sein.
 * Falls das erste Zeichen ein '/' ist, führt das zu keinem doppelten '/' in der
 * URL.</td>
 * <td>/</td>
 * <td>esoves, /esv, /</td>
 * </tr>
 * <tr>
 * <td>module</td>
 * <td>Das REST Modul. Muss angegeben werden und darf nicht null oder leer sein.
 * </td>
 * <td>keiner</td>
 * <td>administration-rest, common-rest, dynatext-rest</td>
 * </tr>
 * <tr>
 * <td>version</td>
 * <td>Die Versionsnummer der Resource. Falls angegeben, darf der Wert nicht
 * null/leer sein.</td>
 * <td>v1</td>
 * <td>r123, v4.2, 56</td>
 * </tr>
 * <tr>
 * <td>resourcePaths</td>
 * <td>Die Pfade der eigentlichen Resource. Muss alle Template Parameter der
 * Resource enthalten. Mindestens ein Pfad muss angegeben werden. Falls das
 * erste Zeichen ein '/' ist, führt das zu keinem doppelten '/' in der URL.</td>
 * <td>keiner</td>
 * <td>[kandidaten, 0815], [/gruppen, 0, /50, name, ASC]</td>
 * </tr>
 * <tr>
 * <td>queryParameter</td>
 * <td>Die optionalen Query Parameter der Resource. Für einen Query Parameter
 * können mehrere Werte angegeben werden. Falls angegeben darf der Name und
 * Wert(e) der Query Parameter nicht null oder leer sein.</td>
 * <td>keine</td>
 * <td>[name=[Pehl], vorname=[Harald, Willi]]</td>
 * </tr>
 * </table>
 * <p>
 * Eine vollständige URL mit allen Bestandteilen sieht also z.B. so aus:<br/>
 * <code>http://www.esoves.de:80/esv/kandidat-rest-v1/kandidaten/0/10/?k0=v0&k1=v1</code>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-12-08 17:33:09 +0100 (Di, 08. Dez 2009) $ $Revision:
 *          77293 $
 */
public class UrlBuilder
{
    // -------------------------------------------------------------- constants

    public static final int PORT_UNSPECIFIED = Integer.MIN_VALUE;
    public static final String DEFAULT_PROTOCOL = "http";
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_CONTEXT = "/";
    public static final String DEFAULT_VERSION = "v1";

    // -------------------------------------------------------- private members

    private String protocol = DEFAULT_PROTOCOL;
    private String host = DEFAULT_HOST;
    private int port = PORT_UNSPECIFIED;
    private String context = DEFAULT_CONTEXT;
    private String module;
    private String version = DEFAULT_VERSION;
    private List<String> resourcePaths = new ArrayList<String>();
    private Map<String, String[]> queryParams = new HashMap<String, String[]>();


    // --------------------------------------------------------- public methods

    /**
     * Build the URL and return it as an encoded string.
     * 
     * @return the encoded URL string
     */
    public final String buildUrl()
    {
        assertState(module, "Module wurde nicht gesetzt. Bitte erst setModule(String) aufrufen!");
        assertState(version, "Version wurde nicht gesetzt. Bitte erst setVersion(String) aufrufen!");
        if (resourcePaths.isEmpty())
        {
            throw new IllegalStateException(
                    "Keine Resource Pfade vorhanden. Bitte erst addResourcePath(String) aufrufen!");
        }
        return internalBuildUrl();
    }


    @Override
    public String toString()
    {
        return internalBuildUrl();
    }


    private String internalBuildUrl()
    {
        // http://
        StringBuilder url = new StringBuilder();
        url.append(protocol).append("://");

        // http://www.esoves.de
        url.append(host);

        // http://www.esoves.de:80
        if (port != PORT_UNSPECIFIED)
        {
            url.append(":").append(port);
        }

        // http://www.esoves.de:80/esv
        url.append(context);

        // http://www.esoves.de:80/esv/kandidat-rest
        if (module != null)
        {
            url.append("/").append(module);
        }

        // Trenner zwischen Modul und Version
        url.append("-");

        // http://www.esoves.de:80/esv/kandidat-rest-v1
        url.append(version);

        // http://www.esoves.de:80/esv/kandidat-rest-v1/kandidaten/0/50
        for (String path : resourcePaths)
        {
            url.append("/").append(path);
        }

        // http://www.esoves.de:80/esv/kandidat-rest-v1/pre/res/params/kandidaten/post/res/params?k0=v0&k1=v1
        char prefix = '?';
        for (Map.Entry<String, String[]> entry : queryParams.entrySet())
        {
            for (String val : entry.getValue())
            {
                url.append(prefix).append(entry.getKey()).append('=');
                if (val != null)
                {
                    url.append(val);
                }
                prefix = '&';
            }
        }

        return URL.encode(url.toString());
    }


    public UrlBuilder setProtocol(String protocol)
    {
        assertValid(protocol, "Protokoll darf nicht null sein");
        if (protocol.endsWith("://"))
        {
            protocol = protocol.substring(0, protocol.length() - 3);
        }
        else if (protocol.endsWith(":/"))
        {
            protocol = protocol.substring(0, protocol.length() - 2);
        }
        else if (protocol.endsWith(":"))
        {
            protocol = protocol.substring(0, protocol.length() - 1);
        }
        if (protocol.contains(":"))
        {
            throw new IllegalArgumentException("Ungültiges Protokoll: " + protocol);
        }
        assertValid(protocol, "Protokoll darf nicht null sein");
        this.protocol = protocol;
        return this;
    }


    public String getProtocol()
    {
        return protocol;
    }


    public UrlBuilder setHost(String host)
    {
        assertValid(host, "Host darf nicht null sein");
        if (host.contains(":"))
        {
            String[] parts = host.split(":");
            if (parts.length > 2)
            {
                throw new IllegalArgumentException("Host enthält mehr als einen Doppelpunkt: " + host);
            }
            try
            {
                setPort(Integer.parseInt(parts[1]));
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Kann den Port nicht aus dem Host auslesen: " + host);
            }
            host = parts[0];
        }
        this.host = host;
        return this;
    }


    public String getHost()
    {
        return host;
    }


    public UrlBuilder setPort(int port)
    {
        if (port < 0)
        {
            throw new IllegalArgumentException("Port darf nicht negativ sein");
        }
        this.port = port;
        return this;
    }


    public int getPort()
    {
        return port;
    }


    public UrlBuilder setContext(String context)
    {
        assertValid(context, "Context darf nicht null sein");
        if (context.startsWith("/"))
        {
            this.context = context;
        }
        else
        {
            this.context = "/" + context;
        }
        return this;
    }


    public String getContext()
    {
        return context;
    }


    public UrlBuilder setModule(String module)
    {
        assertValid(module, "Module darf nicht null sein");
        if (module.startsWith("/"))
        {
            module = module.substring(1);
        }
        this.module = module;
        return this;
    }


    public String getModule()
    {
        return module;
    }


    public UrlBuilder setVersion(String version)
    {
        assertValid(version, "Version darf nicht null sein");
        this.version = version;
        return this;
    }


    public String getVersion()
    {
        return version;
    }


    public UrlBuilder addResourcePath(String... paths)
    {
        assertNotEmpty(paths, "Resource Pfade dürfen nicht leer sein");
        for (String path : paths)
        {
            if (path != null && path.startsWith("/"))
            {
                path = path.substring(1);
            }
            resourcePaths.add(path);
        }
        return this;
    }


    public UrlBuilder clearResourcePaths()
    {
        resourcePaths.clear();
        return this;
    }


    public List<String> getResourcePaths()
    {
        return resourcePaths;
    }


    public UrlBuilder addQueryParameter(String key, String... values)
    {
        assertValid(key, "Schlüssel der Query Paramerter darf nicht null sein");
        assertNotEmpty(values, "Werte der Query Paramerter dürfen nicht leer sein");
        queryParams.put(key, values);
        return this;
    }


    public Map<String, String[]> getQueryParams()
    {
        return queryParams;
    }


    // --------------------------------------------------------- helper methods

    private void assertState(String value, String message) throws IllegalStateException
    {
        if (value == null || value.length() == 0)
        {
            throw new IllegalStateException(message);
        }
    }


    private void assertValid(String value, String message) throws IllegalArgumentException
    {
        if (value == null)
        {
            throw new IllegalArgumentException(message);
        }
        if (value.length() == 0)
        {
            throw new IllegalArgumentException(message);
        }
    }


    private void assertNotEmpty(Object[] values, String message) throws IllegalArgumentException
    {
        if (values == null)
        {
            throw new IllegalArgumentException(message);
        }
        if (values.length == 0)
        {
            throw new IllegalArgumentException(message);
        }
    }
}
