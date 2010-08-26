package name.pehl.taoki.rest.paging.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;
import name.pehl.taoki.rest.paging.SortInfo;

/**
 * {@link PageInfoParser} expecting the paging information in the following
 * format:
 * 
 * <pre>
 * items={offset}-{last-index}[/{sortField}[/{sortDir}]]
 * </pre>
 * 
 * This parser works hand in hand with the
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public class HeaderPageInfoParser implements PageInfoParser
{
    private static final String REGEXP = "^items=([0-9]+)-([0-9]+)(/([\\w]+)(/(asc|desc|none))?)?";


    @Override
    public PageInfo parse(Object input) throws PageInfoParseException
    {
        if (input == null)
        {
            return null;
        }
        if (!(input instanceof String))
        {
            throw new PageInfoParseException("Input hat das falsche Format: Soll: " + String.class.getName()
                    + ", ist: " + input.getClass().getName());
        }

        String header = (String) input;
        Pattern p = Pattern.compile(REGEXP);
        Matcher m = p.matcher(header);

        String offset = null;
        String lastIndex = null;
        String sortField = null;
        String sortDir = null;
        if (m.matches() && m.groupCount() > 1)
        {
            offset = m.group(1);
            lastIndex = m.group(2);
            if (m.groupCount() > 3)
            {
                sortField = m.group(4);
                if (m.groupCount() > 5)
                {
                    sortDir = m.group(6);
                }
            }

            // convert offset
            int offsetValue = 0;
            try
            {
                offsetValue = Integer.parseInt(offset);
            }
            catch (NumberFormatException e)
            {
                throw new PageInfoParseException("Paging information \"" + header + "\" contains the invalid offset \""
                        + offset + "\"");
            }

            // Konvertiere Limit
            int lastIndexValue = 0;
            try
            {
                lastIndexValue = Integer.parseInt(lastIndex);
            }
            catch (NumberFormatException e)
            {
                throw new PageInfoParseException("Paging Information enthält ein ungültiges Limit: " + lastIndex);
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
            int limit = lastIndexValue - offsetValue + 1;
            return new PageInfo(offsetValue, limit, new SortInfo(sortField, sortDirValue));
        }
        else
        {
            throw new PageInfoParseException("Paging Information entspricht nicht dem regulären Ausdruck: " + REGEXP);
        }
    }
}
