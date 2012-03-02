package name.pehl.taoki.paging.parser;

import java.util.Map;

import name.pehl.taoki.paging.PageInfo;

/**
 * {@link PageInfoParser} expecting the paging info as {@link Map} with the
 * following keys
 * <ul>
 * <li>offset
 * <li>pageSize
 * </ul>
 * This parser works hand in hand with the
 * {@link name.pehl.taoki.paging.PagingUrlResource}.
 * 
 * @see name.pehl.taoki.paging.PagingUrlResource
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
     * @see name.pehl.taoki.paging.parser.PageInfoParser#parse(java.lang.Object)
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
        String pageSize = (String) attributes.get(PAGE_SIZE);

        int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
        int pageSizeValue = convertInt(pageSize, "Paging info contains the invalid page size: \"%s\"", pageSize);
        return new PageInfo(offsetValue, pageSizeValue);
    }
}
