package name.pehl.taoki.rest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-12 17:31:45 +0100 (Mo, 12 Jan 2009) $ $Revision:
 *          61016 $
 */
public class PageInfoTest
{
    @Test
    public void testPageInfoIntInt()
    {
        PageInfo underTest = new PageInfo(0, 50);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), 50);
        assertEquals(underTest.getTotal(), 0);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertEquals(underTest.getSortInfo().getSortDir(), SortDir.NONE);
    }


    @Test
    public void testPageInfoIntIntSortInfo()
    {
        PageInfo underTest = new PageInfo(0, 50, new SortInfo("foo", SortDir.DESC));
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), 50);
        assertEquals(underTest.getTotal(), 0);
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
        assertEquals(underTest.getTotal(), 0);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertEquals(underTest.getSortInfo().getSortDir(), SortDir.NONE);

        underTest = new PageInfo(PageInfo.MAX_OFFSET + 1, 50);
        assertEquals(underTest.getOffset(), PageInfo.MAX_OFFSET);
        assertEquals(underTest.getLimit(), 50);
        assertEquals(underTest.getTotal(), 0);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);

        underTest = new PageInfo(0, PageInfo.MIN_LIMIT - 1);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), PageInfo.MIN_LIMIT);
        assertEquals(underTest.getTotal(), 0);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);

        underTest = new PageInfo(0, PageInfo.MAX_LIMIT + 1);
        assertEquals(underTest.getOffset(), 0);
        assertEquals(underTest.getLimit(), PageInfo.MAX_LIMIT);
        assertEquals(underTest.getTotal(), 0);
        assertNotNull(underTest.getSortInfo());
        assertNull(underTest.getSortInfo().getSortField());
        assertTrue(underTest.getSortInfo().getSortDir() == SortDir.NONE);
    }


    @Test
    public void testSublistNull()
    {
        PageInfo pageInfo = new PageInfo(0, 1);
        List<Integer> list = pageInfo.sublist(null);
        assertNull(list);
    }


    @Test
    public void testSublistEmpty()
    {
        PageInfo pageInfo = new PageInfo(0, 1);
        List<Integer> list = new ArrayList<Integer>();
        list = pageInfo.sublist(list);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }


    @Test
    public void testSublist()
    {
        assertSublist(0, 50, 5, 5, 0, 4);
        assertSublist(0, 50, 50, 50, 0, 49);
        assertSublist(50, 50, 125, 50, 50, 99);
        assertSublist(100, 50, 125, 25, 100, 124);
    }


    @Test
    public void testSublistBounds()
    {
        // offset > listSize
        assertSublist(100, 50, 50, 1, 49, 49);
    }


    private void assertSublist(int offset, int limit, int listSize, int pageSize, int firstValue, int lastValue)
    {
        PageInfo pageInfo = new PageInfo(offset, limit);
        List<Integer> list = ListFactory.createList(listSize);
        list = pageInfo.sublist(list);

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(list.size(), pageSize);
        assertTrue(list.get(0) == firstValue);
        assertTrue(list.get(list.size() - 1) == lastValue);
        assertEquals(pageInfo.getTotal(), listSize);
    }

    /**
     * @author $Author: lfstad-pehl $
     * @version $Date: 2009-01-12 17:31:45 +0100 (Mo, 12 Jan 2009) $ $Revision:
     *          61016 $
     */
    private static final class ListFactory
    {
        private ListFactory()
        {
        }


        static List<Integer> createList(int size)
        {
            List<Integer> list = new ArrayList<Integer>(size);
            for (int i = 0; i < size; i++)
            {
                list.add(i);
            }
            return list;
        }
    }
}
