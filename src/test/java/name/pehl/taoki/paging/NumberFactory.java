package name.pehl.taoki.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author$
 * @version $Date$ $Revision: 135
 *          $
 */
public final class NumberFactory
{
    public static List<Integer> numbers(int size)
    {
        List<Integer> list = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++)
        {
            list.add(i);
        }
        return list;
    }


    public static List<Integer> numbers(int start, int end)
    {
        int size = end - start + 1;
        List<Integer> list = new ArrayList<Integer>(size);
        for (int i = start; i <= end; i++)
        {
            list.add(i);
        }
        return list;
    }


    private NumberFactory()
    {
    }
}
