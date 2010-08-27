package name.pehl.taoki.rest.paging.parser;

import java.util.Map;

import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;
import name.pehl.taoki.rest.paging.SortInfo;

/**
 * {@link PageInfoParser} expecting the paging info as {@link Map} with the
 * following keys
 * <ul>
 * <li>offset
 * <li>limit
 * <li>sortField (optional)
 * <li>sortDir (optional)
 * </ul>
 * This parser works hand in hand with the
 * {@link name.pehl.taoki.rest.paging.PagingUrlResource}.
 * 
 * @see name.pehl.taoki.rest.paging.PagingUrlResource
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class UrlPageInfoParser extends AbstractPageInfoParser
{
    /**
     * @param input
     *            a {@link Map} containing the page info keys
     * @return
     * @throws PageInfoParseException
     * @see name.pehl.taoki.rest.paging.parser.PageInfoParser#parse(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageInfo parse(Object input) throws PageInfoParseException
    {
        if (input == null)
        {
            return null;
        }
        verifyInput(input, Map.class);

        Map<String, Object> attributes = (Map<String, Object>) input;
        String offset = (String) attributes.get(OFFSET);
        String limit = (String) attributes.get(LIMIT);
        String sortField = (String) attributes.get(SORT_FIELD);
        String sortDir = (String) attributes.get(SORT_DIR);

        int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
        int limitValue = convertInt(limit, "Paging info contains the invalid limit: \"%s\"", limit);
        SortDir sortDirValue = convertSortDir(sortDir);

        return new PageInfo(offsetValue, limitValue, new SortInfo(sortField, sortDirValue));
    }
}
