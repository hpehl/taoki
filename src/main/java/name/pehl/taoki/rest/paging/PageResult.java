package name.pehl.taoki.rest.paging;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Contains the results for one page of a larger dataset based on a
 * {@link PageInfo} instance.
 * <p>
 * The internal state and behaviour of this class depends on the constructor
 * called:
 * <ol>
 * <li>{@link #PageResult(PageInfo, List)}<br/>
 * In this mode the complete data is known. The methods {@link #first()},
 * {@link #previous()}, {@link #page(int)}, {@link #next()} and {@link #last()}
 * can be used to navigate over the data.
 * <li>{@link #PageResult(PageInfo, List, int)}<br/>
 * In this mode only the data of the specified page is known. The methods
 * {@link #first()}, {@link #previous()}, {@link #page(int)}, {@link #next()}
 * and {@link #last()} all return <code>null</code>.
 * </ol>
 * 
 * @param <T>
 *            The type of the result
 * @author $Author$
 * @version $Date$
 */
public class PageResult<T> implements Iterable<T>
{
    // -------------------------------------------------------------- constants

    public static final int MIN_TOTAL = 0;
    public static final int MAX_TOTAL = 0xffffff;

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------- private members

    private final PageInfo pageInfo;
    private final List<T> all;
    private final List<T> page;
    private final int pages;
    private final int total;


    // ----------------------------------------------------------- constructors

    /**
     * Creates a new instance which contains one page from {@code all} according
     * to {@code pageInfo}. The specified list is expected to contain all data
     * and is used when navigating over it by calling {@link #first()},
     * {@link #previous()}, {@link #page(int)}, {@link #next()} and
     * {@link #last()}. The {@linkplain #total() total number of records} is
     * computed from <code>all.size()</code>.
     * 
     * @param pageInfo
     *            The pageInfo instance which is used to create the sublist.
     *            Must not be <code>null</code>!
     * @param all
     *            The complete list. Might be <code>null</code>. In this case
     *            {@link #total()} will return 0, {@link #page()} will return an
     *            empty list, {@link #isNavigable()} will return
     *            <code>false</code> and {@link #first()}, {@link #previous()},
     *            {@link #page(int)}, {@link #next()} and {@link #last()} will
     *            all return <code>null</code>.
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    public PageResult(final PageInfo pageInfo, final List<T> all)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = all;
        if (all != null)
        {
            this.total = all.size();
            if (total % pageInfo.getPageSize() == 0)
            {
                this.pages = max(1, total / pageInfo.getPageSize());
            }
            else
            {
                this.pages = 1 + total / pageInfo.getPageSize();
            }
            this.page = sublist(pageInfo, all);
        }
        else
        {
            this.total = 0;
            this.pages = 1;
            this.page = Collections.emptyList();
        }
    }


    /**
     * Creates a new instance with exactly the specified data. The complete data
     * is unknown to the created instance and thus the methods {@link #first()},
     * {@link #previous()}, {@link #page(int)}, {@link #next()} and
     * {@link #last()} will all return <code>null</code>. The
     * {@linkplain #total() total number of records} must be specified by the
     * parameter {@code total}. It must be &gt;
     * 
     * @param pageInfo
     *            The pageInfo instance. Only {@link PageInfo#getPageSize()} is
     *            used to shorten the list in case
     *            <code>page.size &gt; pageInfo.getLimit()</code>. Must not be
     *            <code>null</code>
     * @param page
     *            The data for this page.
     * @param total
     *            The total number of records as it cannot be calculated in this
     *            case.
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    public PageResult(final PageInfo pageInfo, final List<T> page, final int total)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = null;
        int validTotal = 0;
        if (total < MIN_TOTAL)
        {
            validTotal = MIN_TOTAL;
        }
        else if (total > MAX_TOTAL)
        {
            validTotal = MAX_TOTAL;
        }
        else
        {
            validTotal = total;
        }
        if (page != null)
        {
            this.page = new ArrayList<T>(page.subList(0, min(page.size(), pageInfo.getPageSize())));
            this.total = max(validTotal, page.size());
        }
        else
        {
            this.page = Collections.emptyList();
            this.total = validTotal;
        }
        this.pages = 1;
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on {@link #pageInfo}, {@link #page()} and {@link #total()}.
     * 
     * @return the hash code based on {@link #pageInfo}, {@link #page()} and
     *         {@link #total()}.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pageInfo == null) ? 0 : pageInfo.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + total;
        return result;
    }


    /**
     * Based on {@link #pageInfo}, {@link #page()} and {@link #total()}.
     * 
     * @param o
     *            the other {@link PageResult} instance
     * @return <code>true</code> if this instance equals {@code o},
     *         <code>false</code> otherwise.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null)
        {
            return false;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
        PageResult<T> other = (PageResult<T>) o;
        if (page == null)
        {
            if (other.page != null)
            {
                return false;
            }
        }
        else if (!page.equals(other.page))
        {
            return false;
        }
        if (pageInfo == null)
        {
            if (other.pageInfo != null)
            {
                return false;
            }
        }
        else if (!pageInfo.equals(other.pageInfo))
        {
            return false;
        }
        if (total != other.total)
        {
            return false;
        }
        return true;
    }


    /**
     * Returns a string representation in the form
     * <code>PageResult[{@link PageInfo#getOffset()}/{@link #size()}/{@link #total()}/{@link #isNavigable()}]</code>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder("PageResult [").append(pageInfo.getOffset()).append("/").append(size()).append("/")
                .append(total()).append("/").append(isNavigable()).append("]").toString();
    }


    // --------------------------------------------------------- helper methods

    /**
     * Throws an {@link IllegalArgumentException} in case {@code pageInfo} is
     * <code>null</code>.
     * 
     * @param pageInfo
     *            The {@link PageInfo} instance to check.
     * @return
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    private PageInfo checkPageInfo(final PageInfo pageInfo) throws IllegalArgumentException
    {
        if (pageInfo == null)
        {
            throw new IllegalArgumentException("pageInfo must not be null");
        }
        return pageInfo;
    }


    /**
     * Creates a sublist of the specified list according to the given
     * {@link PageInfo} instance.
     * 
     * @param pageInfo
     *            The pageInfo instance which is used to create the sublist.
     * @param all
     *            The complete list.
     * @return A sublist which contains only the data according to
     *         {@code pageInfo}.
     */
    private List<T> sublist(final PageInfo pageInfo, final List<T> all)
    {
        if (all.isEmpty())
        {
            return Collections.emptyList();
        }
        if (pageInfo.getOffset() == 0 && all.size() < pageInfo.getPageSize())
        {
            return new ArrayList<T>(all);
        }

        List<T> page = new ArrayList<T>();
        int from = pageInfo.getOffset();
        int to = from + pageInfo.getPageSize();
        int size = all.size();
        if (from >= size)
        {
            from = size - 1;
        }
        if (to > size)
        {
            to = size;
        }
        page.addAll(all.subList(from, to));
        return page;
    }


    // ------------------------------------------------------------- navigation

/**
     * Returns the {@link PageResult} instance representing the first page
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * 
     * @return the {@link PageResult} instance representing the first page 
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     */
    public PageResult<T> first()
    {
        if (isNavigable())
        {
            PageResult<T> first = new PageResult<T>(new PageInfo(0, pageInfo.getPageSize()), all);
            return first;
        }
        return null;
    }


    /**
     * Returns <code>true</code> if this instance is {@linkplain #isNavigable()
     * navigable} and has a previous page, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if this instance is {@linkplain #isNavigable()
     *         navigable} and has a previous page, <code>false</code> otherwise.
     */
    public boolean hasPrevious()
    {
        if (isNavigable())
        {
            int previousOffset = pageInfo.getOffset() - pageInfo.getPageSize();
            return previousOffset >= 0;
        }
        else
        {
            return false;
        }
    }


/**
     * Returns the {@link PageResult} instance representing the previous page
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * 
     * @return the {@link PageResult} instance representing the previous page 
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     */
    public PageResult<T> previous()
    {
        if (isNavigable())
        {
            PageResult<T> previous = new PageResult<T>(pageInfo.previous(), all);
            return previous;
        }
        return null;
    }


/**
     * Returns the {@link PageResult} instance representing the page of the specified index
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * 
     * @param index
     *            the page index
     * @return the {@link PageResult} instance representing the page of the specified index 
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * @throws IndexOutOfBoundsException
     *             if {@code index} &lt; 0 or &gt; {@link #pages()} - 1
     */
    public PageResult<T> page(int index)
    {
        if (index < 0 || index > pages - 1)
        {
            throw new IndexOutOfBoundsException(String.format("Page index %d out of bounds. Valid range: [0, %d]",
                    index, pages - 1));
        }
        if (isNavigable())
        {
            int offset = index * pageInfo.getPageSize();
            PageResult<T> pageAtIndex = new PageResult<T>(new PageInfo(offset, pageInfo.getPageSize()), all);
            return pageAtIndex;
        }
        return null;
    }


    /**
     * Returns <code>true</code> if this instance is {@linkplain #isNavigable()
     * navigable} and has a next page, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if this instance is {@linkplain #isNavigable()
     *         navigable} and has a next page, <code>false</code> otherwise.
     */
    public boolean hasNext()
    {
        if (isNavigable())
        {
            int nextOffset = pageInfo.getOffset() + pageInfo.getPageSize();
            return nextOffset < total();
        }
        return false;
    }


/**
     * Returns the {@link PageResult} instance representing the next page
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * 
     * @return the {@link PageResult} instance representing the next page 
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     */
    public PageResult<T> next()
    {
        if (isNavigable())
        {
            PageResult<T> next = new PageResult<T>(pageInfo.next(), all);
            return next;
        }
        return null;
    }


/**
     * Returns the {@link PageResult} instance representing the last page
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     * 
     * @return the {@link PageResult} instance representing the last page 
     * if this instance is {@linkplain #isNavigable(), <code>null</code> otherwise.
     */
    public PageResult<T> last()
    {
        if (isNavigable())
        {
            int lastOffset = 0;
            if (total() > pageInfo.getPageSize())
            {
                int remainder = total() % pageInfo.getPageSize();
                lastOffset = remainder != 0 ? total() - remainder : total() - pageInfo.getPageSize();
            }
            PageResult<T> last = new PageResult<T>(new PageInfo(lastOffset, pageInfo.getPageSize()), all);
            return last;
        }
        return null;
    }


    // ------------------------------------------------------------- properties

    /**
     * Returns <code>true</code> if this instance was created using
     * {@link #PageResult(PageInfo, List)} and the {@code all} parameter was not
     * <code>null</code>. In this case the methods {@link #first()},
     * {@link #previous()}, {@link #page(int)}, {@link #next()} and
     * {@link #last()} will return a relevant {@link PageResult} instance.
     * <p>
     * If this instance was created using
     * {@link #PageResult(PageInfo, List, int)} the method returns
     * <code>false</code>. In this case the methods {@link #first()},
     * {@link #previous()}, {@link #page(int)}, {@link #next()} and
     * {@link #last()} will all return <code>null</code>.
     * 
     * @return <code>true</code> if this {@link PageResult} is navigable,
     *         <code>false</code> otherwise.
     */
    public boolean isNavigable()
    {
        return all != null;
    }


    /**
     * Returns the data which reflects the current page of this
     * {@link PageResult} instance.
     * 
     * @return the current page data.
     */
    public List<T> page()
    {
        return page;
    }


    /**
     * If this instance contains no data, the method returns 0. If it does the
     * method returns the number of pages if this instance was created using
     * {@link #PageResult(PageInfo, List)}. Otherwise the method returns 1.
     * 
     * @return the number of pages.
     */
    public int pages()
    {
        return pages;
    }


    /**
     * The total number of records.
     * 
     * @return the total number of records.
     */
    public int total()
    {
        return total;
    }


    // -------------------------------------------------- page delegate methods

    /**
     * Delegate for <code>page().iterator()</code>.
     * 
     * @return
     * @see List#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return page.iterator();
    }


    /**
     * Delegate for <code>page().size()</code>.
     * 
     * @return the number of elements in the current page
     * @see List#size()
     */
    public int size()
    {
        return page.size();
    }


    /**
     * Delegate for <code>page().isEmpty()</code>.
     * 
     * @return
     * @see List#isEmpty()
     */
    public boolean isEmpty()
    {
        return page.isEmpty();
    }


    /**
     * Delegate for <code>page().contains()</code>.
     * 
     * @param o
     * @return
     * @see List#contains(Object)
     */
    public boolean contains(Object o)
    {
        return page.contains(o);
    }


    /**
     * Delegate for <code>page().toArray()</code>.
     * 
     * @return
     * @see List#toArray()
     */
    public Object[] toArray()
    {
        return page.toArray();
    }


    /**
     * Delegate for <code>page().toArray(T[])</code>.
     * 
     * @param a
     * @return
     * @see List#toArray(Object[])
     */
    public T[] toArray(T[] a)
    {
        return page.toArray(a);
    }


    /**
     * Delegate for <code>page().containsAll(Collection<?>)</code>.
     * 
     * @param c
     * @return
     * @see List#containsAll(Collection)
     */
    public boolean containsAll(Collection<?> c)
    {
        return page.containsAll(c);
    }


    /**
     * Delegate for <code>page().get(int)</code>.
     * 
     * @param index
     * @return
     * @see List#get(int)
     */
    public T get(int index)
    {
        return page.get(index);
    }


    /**
     * Delegate for <code>page().indexOf(Object)</code>.
     * 
     * @param o
     * @return
     * @see List#indexOf(Object)
     */
    public int indexOf(Object o)
    {
        return page.indexOf(o);
    }


    /**
     * Delegate for <code>page().lastIndexOf(Object)</code>.
     * 
     * @param o
     * @return
     * @see List#lastIndexOf(Object)
     */
    public int lastIndexOf(Object o)
    {
        return page.lastIndexOf(o);
    }


    /**
     * Delegate for <code>page().listIterator()</code>.
     * 
     * @return
     * @see List#listIterator()
     */
    public ListIterator<T> listIterator()
    {
        return page.listIterator();
    }


    /**
     * Delegate for <code>page().listIterator(int)</code>.
     * 
     * @return
     * @see List#listIterator(int)
     */
    public ListIterator<T> listIterator(int index)
    {
        return page.listIterator(index);
    }


    /**
     * Delegate for <code>page().subList(int, int)</code>.
     * 
     * @param fromIndex
     * @param toIndex
     * @return
     * @see List#subList(int, int)
     */
    public List<T> subList(int fromIndex, int toIndex)
    {
        return page.subList(fromIndex, toIndex);
    }
}
