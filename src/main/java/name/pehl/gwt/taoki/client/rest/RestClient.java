package name.pehl.gwt.taoki.client.rest;

import org.restlet.gwt.Client;
import org.restlet.gwt.data.Protocol;
import org.restlet.gwt.representation.Representation;

import com.google.gwt.user.client.Window.Location;

/**
 * Base class for all rest services.
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-09 10:27:04 +0100 (Fr, 09 Jan 2009) $ $Revision:
 *          60744 $
 */
public class RestClient
{
    protected void doGet(String url, RestCallback callback)
    {
        new Client(Protocol.HTTP).get(url, callback);
    }


    protected void doPost(String url, Representation entity, RestCallback callback)
    {
        new Client(Protocol.HTTP).post(url, entity, callback);
    }


    protected void doPut(String url, Representation entity, RestCallback callback)
    {
        new Client(Protocol.HTTP).put(url, entity, callback);
    }


    protected void doDelete(String url, RestCallback callback)
    {
        new Client(Protocol.HTTP).delete(url, callback);
    }


    /**
     * Factory Methode für einen neuen UrlBuilder
     * 
     * @return
     */
    public UrlBuilder newUrlBuilder()
    {
        return new UrlBuilder().setProtocol(Location.getProtocol()).setHost(Location.getHost());
    }
}
