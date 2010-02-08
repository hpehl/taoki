package name.pehl.taoki.rest.paging;

import java.util.Map;

import org.restlet.Request;
import org.restlet.resource.ServerResource;

/**
 * <p>
 * Basisklasse für REST Resourcen, die Paging unterstützen. Die
 * Paginginformationen müssen in der URL folgendermaßen angegeben werden:<br/>
 * <code>http://server/resource/{offset}/{limit}[/{sortField}[/{sortDir}]]</code>
 * </p>
 * <p>
 * Wenn Paging verwendet werden soll, dann müssen die Attribute
 * <code>offset</code> und <code>limit</code> zwingend angegeben werden. Die
 * Attribute <code>sortField</code> und <code>sortDir</code> sind optional. Das
 * Attribut <code>sortDir</code> ist nur in Verbindung mit dem Attribut
 * <code>sortField</code> gültig.
 * </p>
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
 * Die Sortierrichtung (muss einem der Konstanten aus {@link SortDir}
 * entsprechen, wobei Groß/Kleinschreibung keine Rolle spielt)</li>
 * </ol>
 * </p>
 * <p>
 * Im folgenden einige Beispiele gültiger und fehlerhafter URLs:
 * <ol>
 * <li>Gültige URLs<br/>
 * <code>http://server/resource/0/50</code> <br/>
 * <code>http://server/resource/10/20/none</code><br/>
 * <code>http://server/resource/100/50/vorname</code><br/>
 * <code>http://server/resource/1/2/vorname/asc</code><br/>
 * <code>http://server/resource/1/2/plz/dESc</code></li>
 * <li>Fehlerhafte URLs<br/>
 * <code>http://server/resource/-1/50</code> <br/>
 * <code>http://server/resource/100/0</code><br/>
 * <code>http://server/resource/100/50/desc</code> (syntaktisch zwar korrekt,
 * aber es wird hier versucht nach dem <i>Feld</i> <code>desc</code> zu
 * sortieren)<br/>
 * <code>http://server/resource/10/20/nachname/sonicht</code></li>
 * </ol>
 * </p>
 * <p>
 * Falls die Attribute <code>offset</code> und <code>limit</code> außerhalb des
 * gültigen Wertebereich liegen, werden Sie mit den Minimal- bzw. Maximalwert
 * initialisiert.<br/>
 * Falls für <code>offset</code> und <code>limit</code> keine gültigen Zahlen
 * angegeben wurden, oder die Anzahl der Attribute nicht stimmt, liefert die
 * Methode {@link #getPageInfo(Request)} <code>null</code>.
 * </p>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-21 11:32:14 +0100 (Mi, 21 Jan 2009) $ $Revision:
 *          61416 $
 */
public abstract class PagingResource extends ServerResource
{
    public static final String OFFSET_ATTRIBUTE = "offset";
    public static final String LIMIT_ATTRIBUTE = "limit";
    public static final String SORTFIELD_ATTRIBUTE = "sortField";
    public static final String SORTDIR_ATTRIBUTE = "sortDir";


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
     */
    protected PageInfo getPageInfo(Request request)
    {
        Map<String, Object> attributes = request.getAttributes();
        String offset = (String) attributes.get(OFFSET_ATTRIBUTE);
        String limit = (String) attributes.get(LIMIT_ATTRIBUTE);
        String sortField = (String) attributes.get(SORTFIELD_ATTRIBUTE);
        String sortDir = (String) attributes.get(SORTDIR_ATTRIBUTE);

        // Nur dann ein PageInfo zurückgegeben, wenn Offset und Limit
        // angegeben wurden
        if (offset == null || limit == null)
        {
            return null;
        }

        // Konvertiere Offset
        Integer offsetValue = convert(request, offset, "offset");
        if (offsetValue == null)
        {
            return null;
        }

        // Konvertiere Limit
        Integer limitValue = convert(request, limit, "limit");
        if (limitValue == null)
        {
            return null;
        }

        // Konvertiere Sortierungs-Attribute
        SortDir sortDirValue = SortDir.NONE;
        if (sortDir != null)
        {
            try
            {
                sortDirValue = SortDir.valueOf(sortDir.toUpperCase());
            }
            catch (IllegalArgumentException iae)
            {
                getLogger().severe(
                        "Kann Attribute 'sortDir' nicht aus " + request.getResourceRef()
                                + " lesen: Bitte eine gueltige Konstante (" + SortDir.values() + ") angeben.");
                return null;
            }
        }

        // PageInfo erstellen und zurückgeben
        return new PageInfo(offsetValue, limitValue, new SortInfo(sortField, sortDirValue));
    }


    private Integer convert(Request request, String value, String name)
    {
        Integer convertedValue = null;
        try
        {
            convertedValue = new Integer(value);
        }
        catch (NumberFormatException nfe)
        {
            getLogger().severe(
                    "Kann Attribut '" + name + "' nicht aus " + request.getResourceRef()
                            + " lesen: Bitte eine gueltige Zahl angeben.");
        }
        return convertedValue;
    }
}
