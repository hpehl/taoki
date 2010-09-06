package name.pehl.taoki.rest.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;

import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision$
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


    protected void assertPageInfo(PageInfo pageInfo, int offset, int limit)
    {
        assertPageInfo(pageInfo, offset, limit, null);
    }


    protected void assertPageInfo(PageInfo pageInfo, int offset, int limit, String sortField)
    {
        assertPageInfo(pageInfo, offset, limit, sortField, SortDir.NONE);
    }


    protected void assertPageInfo(PageInfo pageInfo, int offset, int limit, String sortField, SortDir sortDir)
    {
        assertNotNull(pageInfo);
        assertNotNull(pageInfo.getSortInfo());
        assertEquals(offset, pageInfo.getOffset());
        assertEquals(limit, pageInfo.getPageSize());
        assertEquals(sortField, pageInfo.getSortInfo().getSortField());
        assertEquals(sortDir, pageInfo.getSortInfo().getSortDir());
    }
}
