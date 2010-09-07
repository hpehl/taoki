package name.pehl.taoki.rest.paging.parser;

import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;
import name.pehl.taoki.rest.paging.SortInfo;

import org.restlet.data.Form;

/**
 * {@link PageInfoParser} expecting the page info as {@link Form} with the
 * following fields
 * <ul>
 * <li>offset
 * <li>limit
 * <li>sortField (optional)
 * <li>sortDir (optional)
 * </ul>
 * This parser works hand in hand with the
 * {@link name.pehl.taoki.rest.paging.PagingQueryResource}.
 * 
 * @see name.pehl.taoki.rest.paging.PagingQueryResource
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class QueryPageInfoParser extends AbstractPageInfoParser
{
    /**
     * @param input
     *            a {@link Form} containing the page info fields
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
        verifyInput(input, Form.class);

        Form form = (Form) input;
        String offset = form.getFirstValue(OFFSET);
        String pageSize = form.getFirstValue(PAGE_SIZE);
        String sortField = form.getFirstValue(SORT_FIELD);
        String sortDir = form.getFirstValue(SORT_DIR);

        int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
        int pageSizeValue = convertInt(pageSize, "Paging info contains the invalid page size: \"%s\"", pageSize);
        SortDir sortDirValue = convertSortDir(sortDir);

        return new PageInfo(offsetValue, pageSizeValue, new SortInfo(sortField, sortDirValue));
    }
}
