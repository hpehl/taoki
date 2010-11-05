package name.pehl.taoki.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.paging.PageInfo;

import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Form;

/**
 * @author $Author$
 * @version $Date$ $Revision: 145
 *          $
 */
public class QueryPageInfoParserTest extends AbstractPageInfoParserTest
{
    @Before
    public void setUp() throws Exception
    {
        underTest = new QueryPageInfoParser();
    }


    @Test
    public void parseInvalid()
    {
        try
        {
            underTest.parse(new Object());
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }
    }


    @Test
    public void parseValid() throws PageInfoParseException
    {
        PageInfo pageInfo = null;

        pageInfo = underTest.parse(formFor(0, 10));
        assertPageInfo(pageInfo, 0, 10);
    }


    private Form formFor(int offset, int pageSize)
    {
        Form form = new Form();
        form.add(PageInfoParser.OFFSET, String.valueOf(offset));
        form.add(PageInfoParser.PAGE_SIZE, String.valueOf(pageSize));
        return form;
    }
}
