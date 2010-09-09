package name.pehl.taoki.rest.paging;

import static name.pehl.taoki.rest.paging.NumberFactory.*;
import static name.pehl.taoki.rest.paging.PageResult.*;
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


    // ---------------------- tests for PageResult(PageInfo, List<T>) instances

    @Test(expected = IllegalArgumentException.class)
    public void allNullPageInfo()
    {
        new PageResult<Integer>(null, null);
    }


    @Test
    public void allNullList()
    {
        PageResult<Integer> underTest = new PageResult<Integer>(PAGE_INFO_0_1, null);
        assertPageResult(underTest, 0, 1, 0, false, 0, 0);
    }


    @Test
    public void allEmptyList()
    {
        PageResult<Integer> underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList());
        assertPageResult(underTest, 0, 1, 0, true, 0, 0);
    }


    @Test
    public void allData()
    {
        PageResult<Integer> underTest = null;

        // limit(10) > total(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5));
        assertPageResult(underTest, 5, 1, 5, true, 0, 4);

        // limit(10) == total(10)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10));
        assertPageResult(underTest, 10, 1, 10, true, 0, 9);

        // limit(10) < total(50)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50));
        assertPageResult(underTest, 10, 5, 50, true, 0, 9);
    }


    @Test
    public void allNavigation()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).first();
        assertPageResult(underTest, 10, 5, 42, true, 0, 9);
        assertFalse(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).previous();
        assertPageResult(underTest, 10, 5, 42, true, 0, 9);
        assertFalse(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).next();
        assertPageResult(underTest, 10, 5, 42, true, 10, 19);
        assertTrue(underTest.hasPrevious());
        assertTrue(underTest.hasNext());

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42));
        for (int i = 0; i < underTest.pages(); i++)
        {
            underTest = underTest.page(i);
            boolean firstPage = i == 0;
            boolean lastPage = i == underTest.pages() - 1;
            assertPageResult(underTest, lastPage ? 2 : 10, 5, 42, true, i * 10, lastPage ? 41 : i * 10 + 9);
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
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).page(-1);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).page(6);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).last();
        assertPageResult(underTest, 2, 5, 42, true, 40, 41);
        assertTrue(underTest.hasPrevious());
        assertFalse(underTest.hasNext());
    }


    // -------------------- test methods for PageResult(PageInfo, List<T>, int)

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
        assertPageResult(underTest, 0, 1, 0, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 0);
        assertPageResult(underTest, 0, 1, 0, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 1);
        assertPageResult(underTest, 0, 1, 1, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, 10);
        assertPageResult(underTest, 0, 1, 10, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, MAX_TOTAL);
        assertPageResult(underTest, 0, 1, MAX_TOTAL, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, null, MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, MAX_TOTAL, false, 0, 0);
    }


    @Test
    public void pageEmptyList()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), -1);
        assertPageResult(underTest, 0, 1, 0, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 0);
        assertPageResult(underTest, 0, 1, 0, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 1);
        assertPageResult(underTest, 0, 1, 1, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), 10);
        assertPageResult(underTest, 0, 1, 10, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), MAX_TOTAL);
        assertPageResult(underTest, 0, 1, MAX_TOTAL, false, 0, 0);
        underTest = new PageResult<Integer>(PAGE_INFO_0_1, Collections.<Integer> emptyList(), MAX_TOTAL + 1);
        assertPageResult(underTest, 0, 1, MAX_TOTAL, false, 0, 0);
    }


    @Test
    public void pageData()
    {
        PageResult<Integer> underTest = null;

        // limit(10) > created list(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), -1);
        assertPageResult(underTest, 5, 1, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 0);
        assertPageResult(underTest, 5, 1, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 4);
        assertPageResult(underTest, 5, 1, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 5);
        assertPageResult(underTest, 5, 1, 5, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), 6);
        assertPageResult(underTest, 5, 1, 6, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), MAX_TOTAL);
        assertPageResult(underTest, 5, 1, MAX_TOTAL, false, 0, 4);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(5), MAX_TOTAL + 1);
        assertPageResult(underTest, 5, 1, MAX_TOTAL, false, 0, 4);

        // limit(10) == created list(5)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), -1);
        assertPageResult(underTest, 10, 1, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 0);
        assertPageResult(underTest, 10, 1, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 9);
        assertPageResult(underTest, 10, 1, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 10);
        assertPageResult(underTest, 10, 1, 10, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), 11);
        assertPageResult(underTest, 10, 1, 11, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), MAX_TOTAL);
        assertPageResult(underTest, 10, 1, MAX_TOTAL, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(10), MAX_TOTAL + 1);
        assertPageResult(underTest, 10, 1, MAX_TOTAL, false, 0, 9);

        // limit(10) < created list(50)
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), -1);
        assertPageResult(underTest, 10, 1, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 0);
        assertPageResult(underTest, 10, 1, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 24);
        assertPageResult(underTest, 10, 1, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 25);
        assertPageResult(underTest, 10, 1, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), 26);
        assertPageResult(underTest, 10, 1, 50, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), MAX_TOTAL);
        assertPageResult(underTest, 10, 1, MAX_TOTAL, false, 0, 9);
        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(50), MAX_TOTAL + 1);
        assertPageResult(underTest, 10, 1, MAX_TOTAL, false, 0, 9);
    }


    @Test
    public void pageNavigation()
    {
        PageResult<Integer> underTest = null;

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42).first();
        assertNull(underTest);

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42).previous();
        assertNull(underTest);

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42).next();
        assertNull(underTest);

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42).page(0);
        assertNull(underTest);
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).page(-1);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42)).page(6);
            fail("IndexOutOfBoundsException expected");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        underTest = new PageResult<Integer>(PAGE_INFO_0_10, numbers(42), 42).last();
        assertNull(underTest);
    }


    // --------------------------------------------------------- helper methods

    private void assertPageResult(PageResult<Integer> underTest, int size, int pages, int total, boolean navigable,
            int firstValue, int lastValue)
    {
        assertNotNull(underTest);
        if (size == 0)
        {
            assertTrue(underTest.isEmpty());
        }
        else
        {
            assertFalse(underTest.isEmpty());
            assertEquals(firstValue, (int) underTest.get(0));
            assertEquals(lastValue, (int) underTest.get(size - 1));
        }
        assertEquals(size, underTest.size());
        assertEquals(pages, underTest.pages());
        assertEquals(total, underTest.total());
        assertEquals(navigable, underTest.isNavigable());
    }
}
