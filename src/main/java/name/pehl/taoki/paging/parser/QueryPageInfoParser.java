package name.pehl.taoki.paging.parser;

import name.pehl.taoki.paging.PageInfo;

import org.restlet.data.Form;

/**
 * {@link PageInfoParser} expecting the page info as {@link Form} with the
 * following fields
 * <ul>
 * <li>offset
 * <li>pageSize
 * </ul>
 * This parser works hand in hand with the
 * {@link name.pehl.taoki.paging.PagingQueryResource}.
 * 
 * @see name.pehl.taoki.paging.PagingQueryResource
 * @author $Author$
 * @version $Date$ $Revision: 145
 *          $
 */
public class QueryPageInfoParser extends AbstractPageInfoParser
{
    /**
     * @param input
     *            a {@link Form} containing the page info fields
     * @return
     * @throws PageInfoParseException
     * @see name.pehl.taoki.paging.parser.PageInfoParser#parse(java.lang.Object)
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

        int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
        int pageSizeValue = convertInt(pageSize, "Paging info contains the invalid page size: \"%s\"", pageSize);
        return new PageInfo(offsetValue, pageSizeValue);
    }
}
