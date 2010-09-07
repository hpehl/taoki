package name.pehl.taoki.rest.paging.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;
import name.pehl.taoki.rest.paging.SortInfo;

/**
 * {@link PageInfoParser} expecting the paging info as string with the following
 * format:
 * 
 * <pre>
 * items={offset}-{last-index}[/{sortField}[/{sortDir}]]
 * </pre>
 * 
 * This parser works hand in hand with the
 * {@link name.pehl.taoki.rest.paging.PagingHeaderResource}.
 * 
 * @see name.pehl.taoki.rest.paging.PagingHeaderResource
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public class HeaderPageInfoParser extends AbstractPageInfoParser
{
    private static final String REGEXP = "^items=([0-9]+)-([0-9]+)(/([\\w]+)(/(asc|desc|none))?)?";


    /**
     * @param input
     *            a string containing the page info data
     * @return
     * @throws PageInfoParseException
     * @see name.pehl.taoki.rest.paging.parser.PageInfoParser#parse(java.lang.Object)
     */
    @Override
    public PageInfo parse(Object input) throws PageInfoParseException
    {
        if (input == null)
        {
            return null;
        }
        verifyInput(input, String.class);

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

            int offsetValue = convertInt(offset, "Paging info \"%s\" contains the invalid offset: \"%s\"", header,
                    offset);
            int lastIndexValue = convertInt(lastIndex, "Paging info \"%s\" contains the invalid last index: \"%s\"",
                    header, lastIndex);
            int pageSize = lastIndexValue - offsetValue + 1;
            SortDir sortDirValue = convertSortDir(sortDir);

            return new PageInfo(offsetValue, pageSize, new SortInfo(sortField, sortDirValue));
        }
        else
        {
            String error = String.format("Paging info has the wrong format. Expected: %s, given: \"%s\"", REGEXP,
                    header);
            throw new PageInfoParseException(error);
        }
    }
}
