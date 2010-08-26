package name.pehl.taoki.rest.paging.parser;


import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;
import name.pehl.taoki.rest.paging.SortInfo;

import org.restlet.data.Form;

/**
 * {@link PageInfoParser} der die Paging Information im folgenden Format
 * erwartet: <code>{offset}/{limit}[/{sortField}[/{sortDir}]]</code>.
 * 
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class QueryPageInfoParser implements PageInfoParser
{
    @Override
    public PageInfo parse(Object input) throws PageInfoParseException
    {
        if (input == null)
        {
            return null;
        }
        if (!(input instanceof Form))
        {
            throw new PageInfoParseException("Input hat das falsche Format: Soll: " + Form.class.getName() + ", ist: "
                    + input.getClass().getName());
        }

        Form form = (Form) input;
        String offset = form.getFirstValue(OFFSET);
        String limit = form.getFirstValue(LIMIT);
        String sortField = form.getFirstValue(SORT_FIELD);
        String sortDir = form.getFirstValue(SORT_DIR);

        // Konvertiere Offset
        int offsetValue = 0;
        try
        {
            offsetValue = Integer.parseInt(offset);
        }
        catch (NumberFormatException e)
        {
            throw new PageInfoParseException("Paging Information enthält einen ungültigen Offset: " + offset);
        }

        // Konvertiere Limit
        int limitValue = 0;
        try
        {
            limitValue = Integer.parseInt(limit);
        }
        catch (NumberFormatException e)
        {
            throw new PageInfoParseException("Paging Information enthält ein ungültiges Limit: " + limit);
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
                throw new PageInfoParseException("Paging Information enthält eine ungültige Sortierungsrichtung: "
                        + sortDir + ". Bitte eine gueltige Konstante (" + SortDir.values() + ") angeben.");
            }
        }

        // PageInfo erstellen und zurückgeben
        return new PageInfo(offsetValue, limitValue, new SortInfo(sortField, sortDirValue));
    }
}
