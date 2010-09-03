package name.pehl.taoki.rest.paging;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class PageResultTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testNullPageInfo()
    {
        new PageResult<Integer>(null, null);
    }


    @Test
    public void testNullList()
    {
        PageResult<Integer> pageResult = new PageResult<Integer>(new PageInfo(0, 1), null);
        assertTrue(pageResult.isEmpty());
        assertEquals(0, pageResult.total());
    }


    @Test
    public void testEmptyList()
    {
        PageResult<Integer> pageResult = new PageResult<Integer>(new PageInfo(0, 1), Collections.<Integer> emptyList());
        assertTrue(pageResult.isEmpty());
        assertEquals(0, pageResult.total());
    }


    @Test
    public void testPageResult()
    {
//        assertPageResult(0, 50, 5, 5, 0, 4);
//        assertPageResult(0, 50, 50, 50, 0, 49);
//        assertPageResult(50, 50, 125, 50, 50, 99);
//        assertPageResult(100, 50, 125, 25, 100, 124);
//        // offset > listSize
//        assertPageResult(100, 50, 50, 1, 49, 49);
    }


    private void assertPageResult(int offset, int limit, int listSize, int pageSize, int firstValue, int lastValue)
    {
        PageInfo pageInfo = new PageInfo(offset, limit);
        List<Integer> list = ListFactory.createList(listSize);
        PageResult<Integer> pageResult = new PageResult<Integer>(pageInfo, list);

        assertEquals(pageSize, pageResult.size());
        assertEquals(firstValue, (int) pageResult.get(0));
        assertEquals(lastValue, (int) pageResult.get(list.size() - 1));
        assertEquals(listSize, pageResult.total());
    }

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
