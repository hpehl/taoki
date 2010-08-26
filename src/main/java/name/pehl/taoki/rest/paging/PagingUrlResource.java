package name.pehl.taoki.rest.paging;

import java.util.logging.Level;

import name.pehl.taoki.rest.paging.parser.PageInfoParseException;
import name.pehl.taoki.rest.paging.parser.PageInfoParser;
import name.pehl.taoki.rest.paging.parser.UrlPageInfoParser;

import org.restlet.Request;
import org.restlet.resource.ServerResource;

/**
 * Basisklasse für REST Resourcen, die Paging unterstützen. Die
 * Paginginformationen müssen in der URL folgendermaßen angegeben werden:<br/>
 * <code>http://server/resource/{offset}/{limit}[/{sortField}[/{sortDir}]]</code>
 * <p>
 * Wenn Paging verwendet werden soll, dann müssen die Attribute
 * <code>offset</code> und <code>limit</code> zwingend angegeben werden. Die
 * Attribute <code>sortField</code> und <code>sortDir</code> sind optional. Das
 * Attribut <code>sortDir</code> ist nur in Verbindung mit dem Attribut
 * <code>sortField</code> gültig.
 * <p>
 * Hier die Bedeutung der einzelnen Attribute:
 * <ol>
 * <li><code>offset</code><br/>
 * Der Offset für das Paging (muß zwischen {@link PageInfo#MIN_OFFSET} und
 * {@link PageInfo#MAX_OFFSET} liegen)</li>
 * <li><code>limit</code><br/>
 * Das Limit (PageSize) für das Paging (muß zwischen {@link PageInfo#MIN_LIMIT}
 * und {@link PageInfo#MAX_LIMIT} liegen)</li>
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
 * <li>http://server/resource/0/50
 * <li>http://server/resource/10/20/none
 * <li>http://server/resource/100/50/vorname
 * <li>http://server/resource/1/2/vorname/asc
 * <li>http://server/resource/1/2/plz/dESc
 * </ul>
 * <p>
 * Falls die Attribute <code>offset</code> und <code>limit</code> außerhalb des
 * gültigen Wertebereich liegen, werden Sie mit den Minimal- bzw. Maximalwert
 * initialisiert.<br/>
 * Falls für <code>offset</code> und <code>limit</code> keine gültigen Zahlen
 * angegeben wurden, oder die Anzahl der Attribute nicht stimmt, liefert die
 * Methode {@link #getPageInfo(Request)} <code>null</code>.
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-21 11:32:14 +0100 (Mi, 21 Jan 2009) $ $Revision:
 *          61416 $
 */
public abstract class PagingUrlResource extends ServerResource implements HasPageInfo
{
    /**
     * Prüft den Request auf Paging Attribute und liefert die entsprechende
     * {@link PageInfo} Instanz oder <code>null</code>, falls für
     * <code>offset</code> und <code>limit</code> keine gültigen Zahlen
     * angegeben wurden, oder die Anzahl der Attribute nicht stimmt.
     * 
     * @param request
     *            Der Request der Resource
     * @return Eine {@link PageInfo} Instanz mit den im Request gemachten
     *         Attrbuten bzw. <code>null</code>, falls für <code>offset</code>
     *         und <code>limit</code> keine gültigen Zahlen angegeben wurden,
     *         oder die Anzahl der Attribute nicht stimmt.
     * @see de.bayern.lfstad.esoves.rest.HasPageInfo#getPageInfo(org.restlet.Request)
     */
    @Override
    public PageInfo getPageInfo(Request request)
    {
        PageInfo pageInfo = null;
        PageInfoParser urlParser = new UrlPageInfoParser();
        try
        {
            pageInfo = urlParser.parse(request.getAttributes());
        }
        catch (PageInfoParseException e)
        {
            getLogger().log(
                    Level.SEVERE,
                    "Paging Informationen können nicht aus " + request.getResourceRef() + " gelesen werden: "
                            + e.getMessage(), e);
        }
        return pageInfo;
    }
}
