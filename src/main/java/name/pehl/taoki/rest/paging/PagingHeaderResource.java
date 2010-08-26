package name.pehl.taoki.rest.paging;

import java.util.logging.Level;

import name.pehl.taoki.rest.paging.parser.HeaderPageInfoParser;
import name.pehl.taoki.rest.paging.parser.PageInfoParseException;
import name.pehl.taoki.rest.paging.parser.PageInfoParser;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.resource.ServerResource;

/**
 * Basisklasse für REST Resourcen, die Paging unterstützen. Die
 * Paginginformationen müssen im Header <a
 * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35"
 * >Range</a> folgendermaßen angegeben werden:<br/>
 * <code>Range: items={offset}-{last-index}[/{sortField}[/{sortDir}]]</code><br/>
 * <p>
 * Wenn Paging verwendet werden soll, dann müssen die Attribute
 * <code>offset</code> und <code>last-index</code> zwingend angegeben werden.
 * Die Attribute <code>sortField</code> und <code>sortDir</code> sind optional.
 * Das Attribut <code>sortDir</code> ist nur in Verbindung mit dem Attribut
 * <code>sortField</code> gültig.
 * <p>
 * Hier die Bedeutung der einzelnen Attribute:
 * <ol>
 * <li><code>offset</code><br/>
 * Der Offset für das Paging (muß zwischen {@link PageInfo#MIN_OFFSET} und
 * {@link PageInfo#MAX_OFFSET} liegen)</li>
 * <li><code>last-index</code><br/>
 * Der letzte Index der noch im Ergebnis enthalten sein soll. Der Wert muss
 * größer als <code>offset</code> und kleiner als {@link PageInfo#MAX_OFFSET}
 * sein.</li>
 * <li><code>sortField</code><br/>
 * Der Name des Feldes nach dem sortiert werden soll</li>
 * <li><code>sortDir</code><br/>
 * Die Sortierrichtung (muss einem der Konstanten aus
 * {@link de.bayern.lfstad.esoves.commons.paging.SortDir} entsprechen, wobei
 * Groß/Kleinschreibung keine Rolle spielt)</li>
 * </ol>
 * <p>
 * Beispiele:
 * <ul>
 * <li>Range: items=0-24
 * <li>Range: items=25-49/nachname
 * <li>Range: items=100-124/erzeugtAm/dESC
 * </ul>
 * <p>
 * Falls die Attribute <code>offset</code> und <code>last-index</code> außerhalb
 * des gültigen Wertebereich liegen, werden Sie mit den Minimal- bzw.
 * Maximalwert initialisiert.<br/>
 * Falls für <code>offset</code> und <code>last-index</code> keine gültigen
 * Zahlen angegeben wurden, oder die Anzahl der Attribute nicht stimmt, liefert
 * die Methode {@link #getPageInfo(Request)} <code>null</code>.
 * <p>
 * TODO Der <a
 * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35"
 * >Range</a> Header wird hier nicht ganz im Sinne des Erfinders verwendet.
 * Falls irgendwelche Proxies sich daran stören, muss in der Antwort ggf. noch
 * folgender Header mit aufgenommen werden:<br/>
 * 
 * <pre>
 * Content-Range: items {offset}-{lastIndex}/{total}
 * </pre>
 * 
 * <code>{total}</code> steht dabei für die Gesamtzahl der Datensätze.
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public class PagingHeaderResource extends ServerResource implements HasPageInfo
{
    /**
     * Prüft den Request auf Paging Attribute und liefert die entsprechende
     * {@link PageInfo} Instanz oder <code>null</code>, falls für
     * <code>offset</code> und <code>last-index</code> keine gültigen Zahlen
     * angegeben wurden, oder die Anzahl der Attribute nicht stimmt.
     * 
     * @param request
     *            Der Request der Resource
     * @return Eine {@link PageInfo} Instanz mit den im Request gemachten
     *         Attrbuten bzw. <code>null</code>, falls für <code>offset</code>
     *         und <code>last-index</code> keine gültigen Zahlen angegeben
     *         wurden, oder die Anzahl der Attribute nicht stimmt.
     * @see de.bayern.lfstad.esoves.rest.HasPageInfo#getPageInfo(org.restlet.Request)
     */
    @Override
    public PageInfo getPageInfo(Request request)
    {
        PageInfo pageInfo = null;
        // request.getRanges() funktioniert hier leider nicht, da der
        // RangeReader das Format "items=..." nicht verarbeiten kann.
        Object object = request.getAttributes().get("org.restlet.http.headers");
        if (object != null && object instanceof Form)
        {
            Form headers = (Form) object;
            String rangeHeader = headers.getFirstValue("range");
            PageInfoParser headerParser = new HeaderPageInfoParser();
            try
            {
                pageInfo = headerParser.parse(rangeHeader);
            }
            catch (PageInfoParseException e)
            {
                getLogger().log(
                        Level.SEVERE,
                        "Paging Informationen können nicht aus " + request.getResourceRef() + " gelesen werden: "
                                + e.getMessage(), e);
            }
        }
        return pageInfo;
    }
}
