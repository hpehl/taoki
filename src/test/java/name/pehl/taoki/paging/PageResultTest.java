package name.pehl.taoki.paging;

import static name.pehl.taoki.paging.NumberFactory.numbers;
import static name.pehl.taoki.paging.PageResult.MAX_TOTAL;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision: 117
 *          $
 */
public class PageResultTest
{
    // -------------------------------------------------------------- constants

    private static final PageInfo PAGE_INFO_0_1 = new PageInfo(0, 1);
    private static final PageInfo PAGE_INFO_0_10 = new PageInfo(0, 10);


    // --------------------------------------- tests for navigable page results

    @Test(expected = IllegalArgumentException.class)
    public void allNullPageInfo()
    {
        new PageResult<Integer>(null, null);
    }


    @Test
    public void allNullList()
    {
        PageResult<Integer> underTest = new PageResult<Integer>(PAGE_INFO_0_1, null);
        assertPageResult(underTest, 0, 1, 0, 0, false);
    }


    @Test
    public void allEmptyList()
    {
        PageResult<Integer> underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList());
        assertPageResult(underTest, 0, 1, 0, 0, true);
    }


    @Test
    public void allData()
    {
        PageResult<Integer> underTest = null;

        // limit(10) > total(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5));
        assertPageResult(underTest, 0, 1, 5, 5, true, 0, 4);

        // limit(10) == total(10)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10));
        assertPageResult(underTest, 0, 1, 10, 10, true, 0, 9);

        // limit(10) < total(50)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50));
        assertPageResult(underTest, 0, 5, 10, 50, true, 0, 9);

        // limit(10) < total(50), 2nd page
        underTest = new PageResult<Integer>(PAGE_INFO_0_10.next(), numbers(50));
        assertPageResult(underTest, 1, 5, 10, 50, true, 10, 19);
    }


    @Test
    public void allNavigation()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).first();
        assertPageResult(underTest, 0, 5, 10, 42, true, 0, 9);
        assertFalse(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).previous();
        assertPageResult(underTest, 0, 5, 10, 42, true, 0, 9);
        assertFalse(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).next();
        assertPageResult(underTest, 1, 5, 10, 42, true, 10, 19);
        assertTrue(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42));
        for (int i = 0; i < underTest.getPages(); i++)
        {
            underTest = underTest.getPage(i);
            boolean firstPage = i == 0;
            boolean lastPage = i == underTest.getPages() - 1;
            assertPageResult(underTest, i, 5, lastPage ? 2 : 10, 42, true, i * 10, lastPage ? 41 : i * 10 + 9);
            if (firstPage)
            {
                assertFalse(underTest.hasPrevious());
                assertTrue(underTest.hasNext());
            }
            else if (lastPage)
            {
                assertTrue(underTest.hasPrevious());
                assertFalse(underTest.hasNext());
            }
            else
            {
                assertTrue(underTest.hasPrevious());
                assertTrue(underTest.hasNext());
            }
        }
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).getPage(-1);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).getPage(6);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).last();
        assertPageResult(underTest, 4, 5, 2, 42, true, 40, 41);
        assertTrue(underTest.hasPrevious());
        assertFalse(underTest.hasNext());
    }


    // ----------------------------------- tests for non-navigable page results

    @Test
    public void pageNullPageInfo()
    {
        try
        {
            new PageResult<Integer>(null, null, -1);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new PageResult<Integer>(null, null, 0);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new PageResult<Integer>(null, null, 1);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new PageResult<Integer>(null, null, MAX_TOTAL + 1);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    @Test
    public void pageNullList()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, -1);
        assertPageResult(underTest, 0, 1, 0, 0, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 0);
        assertPageResult(underTest, 0, 1, 0, 0, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 1);
        assertPageResult(underTest, 0, 1, 0, 1, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 10);
        assertPageResult(underTest, 0, 1, 0, 10, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, MAX_TOTAL);
        assertPageResult(underTest, 0, 1, 0, MAX_TOTAL, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, 0, MAX_TOTAL, false);
    }


    @Test
    public void pageEmptyList()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), -1);
        assertPageResult(underTest, 0, 1, 0, 0, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 0);
        assertPageResult(underTest, 0, 1, 0, 0, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 1);
        assertPageResult(underTest, 0, 1, 0, 1, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 10);
        assertPageResult(underTest, 0, 1, 0, 10, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), MAX_TOTAL);
        assertPageResult(underTest, 0, 1, 0, MAX_TOTAL, false);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, 0, MAX_TOTAL, false);
    }


    @Test
    public void pageData()
    {
        PageResult<Integer> underTest = null;

        // limit(10) > created list(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), -1);
        assertPageResult(underTest, 0, 1, 5, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 0);
        assertPageResult(underTest, 0, 1, 5, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 4);
        assertPageResult(underTest, 0, 1, 5, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 5);
        assertPageResult(underTest, 0, 1, 5, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 6);
        assertPageResult(underTest, 0, 1, 5, 6, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), MAX_TOTAL);
        assertPageResult(underTest, 0, 1, 5, MAX_TOTAL, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, 5, MAX_TOTAL, false, 0, 4);

        // limit(10) == created list(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), -1);
        assertPageResult(underTest, 0, 1, 10, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 0);
        assertPageResult(underTest, 0, 1, 10, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 9);
        assertPageResult(underTest, 0, 1, 10, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 10);
        assertPageResult(underTest, 0, 1, 10, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 11);
        assertPageResult(underTest, 0, 1, 10, 11, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), MAX_TOTAL);
        assertPageResult(underTest, 0, 1, 10, MAX_TOTAL, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, 10, MAX_TOTAL, false, 0, 9);

        // limit(10) < created list(50)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), -1);
        assertPageResult(underTest, 0, 1, 10, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 0);
        assertPageResult(underTest, 0, 1, 10, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 24);
        assertPageResult(underTest, 0, 1, 10, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 25);
        assertPageResult(underTest, 0, 1, 10, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 26);
        assertPageResult(underTest, 0, 1, 10, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), MAX_TOTAL);
        assertPageResult(underTest, 0, 1, 10, MAX_TOTAL, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, 10, MAX_TOTAL, false, 0, 9);
    }


    @Test
    public void pageNavigation()
    {
        PageResult<Integer> underTest = null;
        PageResult<Integer> pageResult = null;

        pageResult = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42);
        underTest = pageResult.first();
        assertSame(pageResult, underTest);

        underTest = pageResult.previous();
        assertSame(pageResult, underTest);

        underTest = pageResult.next();
        assertSame(pageResult, underTest);

        underTest = pageResult.getPage(0);
        assertSame(pageResult, underTest);
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).getPage(-1);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).getPage(6);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        underTest = pageResult.last();
        assertSame(pageResult, underTest);
    }


    // --------------------------------------------------------- helper methods

    private void assertPageResult(PageResult<Integer> underTest, int pageIndex, int pages, int size, int total,
            boolean navigable, int... firstAndLastValue)
    {
        assertNotNull(underTest);
        assertEquals(pageIndex, underTest.getPageIndex());
        assertEquals(pages, underTest.getPages());
        assertEquals(size, underTest.size());
        assertEquals(size == 0, underTest.isEmpty());
        assertEquals(total, underTest.getTotal());
        assertEquals(navigable, underTest.isNavigable());
        if (firstAndLastValue != null && firstAndLastValue.length == 2)
        {
            assertEquals(firstAndLastValue[0], (int) underTest.get(0));
            assertEquals(firstAndLastValue[1], (int) underTest.get(size - 1));
        }
    }
}
