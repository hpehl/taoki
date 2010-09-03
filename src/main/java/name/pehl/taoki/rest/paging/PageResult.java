package name.pehl.taoki.rest.paging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Contains the results for one page of a larger dataset based on an
 * {@link PageInfo} instance.
 * 
 * @param <T>
 *            The type of the result
 * @author $Author$
 * @version $Date$
 */
public class PageResult<T> implements Iterable<T>
{
    // -------------------------------------------------------------- constants

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------- private members

    private final PageInfo pageInfo;
    private final List<T> all;
    private final List<T> page;
    private final int total;


    // ----------------------------------------------------------- constructors

    /**
     * @param pageInfo
     * @param all
     */
    public PageResult(final PageInfo pageInfo, List<T> all)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = all;
        if (all != null)
        {
            this.total = all.size();
            this.page = sublist(all);
        }
        else
        {
            this.total = 0;
            this.page = Collections.emptyList();
        }
    }


    /**
     * @param pageInfo
     * @param page
     * @param total
     */
    public PageResult(final PageInfo pageInfo, List<T> page, int total)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = null;
        if (page != null)
        {
            this.total = total;
            this.page = new ArrayList<T>();
            if (page.size() > pageInfo.getLimit())
            {
                this.page.addAll(page.subList(0, pageInfo.getLimit()));
            }
            else
            {
                this.page.addAll(page);
            }
        }
        else
        {
            this.total = 0;
            this.page = Collections.emptyList();
        }
    }


    // --------------------------------------------------------- helper methods

    private PageInfo checkPageInfo(PageInfo pageInfo) throws IllegalArgumentException
    {
        if (pageInfo == null)
        {
            throw new IllegalArgumentException("pageInfo must not be null");
        }
        return pageInfo;
    }


    private List<T> sublist(List<T> list)
    {
        if (list.isEmpty())
        {
            return Collections.emptyList();
        }
        if (pageInfo.getOffset() == 0 && list.size() < pageInfo.getLimit())
        {
            return new ArrayList<T>(list);
        }

        List<T> page = new ArrayList<T>();
        int from = pageInfo.getOffset();
        int to = from + pageInfo.getLimit();
        int size = list.size();
        if (from >= size)
        {
            from = size - 1;
        }
        if (to > size)
        {
            to = size;
        }
        page.addAll(list.subList(from, to));
        return page;
    }


    // --------------------------------------------------------- public methods

    public boolean hasPrevious()
    {
        PageResult<T> previous = previous();
        return previous != null && !previous.isEmpty();
    }


    public PageResult<T> previous()
    {
        if (all != null)
        {
            PageInfo previousPageInfo = pageInfo.previous();
            PageResult<T> previousPageResult = new PageResult<T>(previousPageInfo, all);
            return previousPageResult;
        }
        return null;
    }


    public List<T> page()
    {
        return page;
    }


    public int total()
    {
        return total;
    }


    // ------------------------------------------------ result delegate methods

    @Override
    public Iterator<T> iterator()
    {
        return page.iterator();
    }


    public int size()
    {
        return page.size();
    }


    public boolean isEmpty()
    {
        return page.isEmpty();
    }


    public boolean contains(Object o)
    {
        return page.contains(o);
    }


    public Object[] toArray()
    {
        return page.toArray();
    }


    public T[] toArray(T[] a)
    {
        return page.toArray(a);
    }


    public boolean containsAll(Collection<?> c)
    {
        return page.containsAll(c);
    }


    public T get(int index)
    {
        return page.get(index);
    }


    public int indexOf(Object o)
    {
        return page.indexOf(o);
    }


    public int lastIndexOf(Object o)
    {
        return page.lastIndexOf(o);
    }


    public ListIterator<T> listIterator()
    {
        return page.listIterator();
    }


    public ListIterator<T> listIterator(int index)
    {
        return page.listIterator(index);
    }


    public List<T> subList(int fromIndex, int toIndex)
    {
        return page.subList(fromIndex, toIndex);
    }
}
