package name.pehl.taoki.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.PageInfoParseException;

import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision: 145
 *          $
 */
public abstract class AbstractPageInfoParserTest
{
    protected static final String FOO = "foo";
    protected static final String PAGE_INFO_PARSE_EXCEPTION_EXPECTED = PageInfoParseException.class.getSimpleName()
            + " erwartet!";

    protected PageInfoParser underTest;


    @Test
    public void parseNull() throws PageInfoParseException
    {
        PageInfo pageInfo = underTest.parse(null);
        assertNull(pageInfo);
    }


    @Test(expected = PageInfoParseException.class)
    public void parseWrongInput() throws PageInfoParseException
    {
        underTest.parse(new Object());
    }


    protected void assertPageInfo(PageInfo pageInfo, int offset, int pageSize)
    {
        assertNotNull(pageInfo);
        assertEquals(offset, pageInfo.getOffset());
        assertEquals(pageSize, pageInfo.getPageSize());
    }
}
