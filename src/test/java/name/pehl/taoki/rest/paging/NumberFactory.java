package name.pehl.taoki.rest.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
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


    private NumberFactory()
    {
    }
}
