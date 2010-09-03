package name.pehl.taoki.rest.paging;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-12 17:31:45 +0100 (Mo, 12 Jan 2009) $ $Revision:
 *          61016 $
 */
public class PageInfoTest
{
    @Test
    public void testPageInfoWithOffsetLimit()
    {
        PageInfo underTest = new PageInfo(0, 50);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), 50);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertEquals(underTest.getSortInfo().getSortDir(), SortDir.NONE);
    }


    @Test
    public void testPageInfoWithOffsetLimitSortInfo()
    {
        PageInfo underTest = new PageInfo(0, 50, new SortInfo("foo", SortDir.DESC));
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), 50);
        assertNotNull(underTest.getSortInfo());
        assertEquals(underTest.getSortInfo().getSortField(), "foo");
        assertEquals(underTest.getSortInfo().getSortDir(), SortDir.DESC);
    }


    @Test
    public void testPageInfoBounds()
    {
        PageInfo underTest = new PageInfo(PageInfo.MIN_OFFSET - 1, 50);
        assertEquals(underTest.getOffset(), PageInfo.MIN_OFFSET);
        assertEquals(underTest.getLimit(), 50);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertEquals(underTest.getSortInfo().getSortDir(), SortDir.NONE);

        underTest = new PageInfo(PageInfo.MAX_OFFSET + 1, 50);
        assertEquals(underTest.getOffset(), PageInfo.MAX_OFFSET);
        assertEquals(underTest.getLimit(), 50);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);

        underTest = new PageInfo(0, PageInfo.MIN_LIMIT - 1);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), PageInfo.MIN_LIMIT);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);

        underTest = new PageInfo(0, PageInfo.MAX_LIMIT + 1);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), PageInfo.MAX_LIMIT);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);
    }
}
