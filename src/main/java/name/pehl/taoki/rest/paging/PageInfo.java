package name.pehl.taoki.rest.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Value object for paging over large data. The class holds the following
 * information:
 * <ul>
 * <li>offset
 * <li>limit
 * <li>{@linkplain SortInfo sortInfo}
 * </ul>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-28 09:37:21 +0100 (Mi, 28 Jan 2009) $ $Revision:
 *          61016 $
 */
public class PageInfo implements Serializable
{
    public static final int MIN_OFFSET = 0;
    public static final int MAX_OFFSET = 0xffffff / 2;
    public static final int MIN_LIMIT = 1;
    public static final int MAX_LIMIT = 0xffffff;

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(PageInfo.class.getName());

    private int offset;
    private int limit;
    private SortInfo sortInfo;
    private int total;


    /**
     * Construct a new instance using the specified offset and limit.
     * 
     * @param offset
     *            The offset. If &lt; {@value #MIN_OFFSET}, offset is set to
     *            {@value #MIN_OFFSET}. If &gt; {@value #MAX_OFFSET}, offset is
     *            set to {@value #MAX_OFFSET}.
     * @param limit
     *            The limit. If &lt; {@value #MIN_LIMIT}, offset is set to
     *            {@value #MIN_LIMIT}. If &gt; {@value #MAX_LIMIT}, offset is
     *            set to {@value #MAX_LIMIT}.
     */
    public PageInfo(int offset, int limit)
    {
        this(offset, limit, new SortInfo());
    }


    /**
     * Construct a new instance using the specified offset, limit and sort info.
     * 
     * @param offset
     *            The offset. If &lt; {@value #MIN_OFFSET}, offset is set to
     *            {@value #MIN_OFFSET}. If &gt; {@value #MAX_OFFSET}, offset is
     *            set to {@value #MAX_OFFSET}.
     * @param limit
     *            The limit. If &lt; {@value #MIN_LIMIT}, offset is set to
     *            {@value #MIN_LIMIT}. If &gt; {@value #MAX_LIMIT}, offset is
     *            set to {@value #MAX_LIMIT}.
     * @param sortInfo
     */
    public PageInfo(int offset, int limit, SortInfo sortInfo)
    {
        super();
        if (offset < MIN_OFFSET)
        {
            this.offset = MIN_OFFSET;
            log.warning("Offset '" + offset + "' is less than the allowed minimum '" + MIN_OFFSET
                    + "'. Offset is set to the minimum.");
        }
        else if (offset > MAX_OFFSET)
        {
            this.offset = MAX_OFFSET;
            log.warning("Offset '" + offset + "' is greater than the allowed maximum '" + MAX_OFFSET
                    + "'. Set offset to the maximum.");
        }
        else
        {
            this.offset = offset;
        }
        if (limit < MIN_LIMIT)
        {
            this.limit = MIN_LIMIT;
            log.warning("Limit '" + limit + "' is less than the allowed minimum '" + MIN_LIMIT
                    + "'. Set limit to the minimum.");
        }
        else if (limit > MAX_LIMIT)
        {
            this.limit = MAX_LIMIT;
            log.warning("Limit '" + limit + "' is greater than the allowed maximum '" + MAX_LIMIT
                    + "'. Set limit to the maximum.");
        }
        else
        {
            this.limit = limit;
        }
        this.sortInfo = sortInfo;
        this.total = 0;
    }


    /**
     * Returns a sublist of the specified list according to the offset and limit
     * of this PageInfo. The total is set to the size of the specified list.
     * 
     * @param <E>
     * @param list
     * @return
     */
    public <E> List<E> sublist(List<E> list)
    {
        // Kein Paging nötig
        if (list == null || list.isEmpty())
        {
            setTotal(0);
            return list;
        }
        setTotal(list.size());
        if (getOffset() == 0 && list.size() < getLimit())
        {
            return list;
        }

        int from = getOffset();
        int to = from + getLimit();
        List<E> paged = new ArrayList<E>();
        List<E> collectionAsList = new ArrayList<E>(list);
        int size = list.size();
        if (from >= size)
        {
            from = size - 1;
        }
        if (to > size)
        {
            to = size;
        }
        paged.addAll(collectionAsList.subList(from, to));
        return paged;
    }


    /**
     * Based on {@code offset}, {@code limit} and {@code sortInfo}
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + offset;
        result = prime * result + limit;
        result = prime * result + ((sortInfo == null) ? 0 : sortInfo.hashCode());
        return result;
    }


    /**
     * Based on {@code offset}, {@code limit} and {@code sortInfo}
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        PageInfo other = (PageInfo) obj;
        if (offset != other.offset)
        {
            return false;
        }
        if (limit != other.limit)
        {
            return false;
        }
        if (sortInfo == null)
        {
            if (other.sortInfo != null)
            {
                return false;
            }
        }
        else if (!sortInfo.equals(other.sortInfo))
        {
            return false;
        }
        return true;
    }


    /**
     * Returns a string representation in the form
     * <code>PageInfo[&lt;offset&gt;/&lt;limit&gt;/&lt;total&gt;/&lt;sortInfo&gt;]</code>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder("PageInfo [").append(offset).append("/").append(limit).append("/").append(total)
                .append("/").append(sortInfo).toString();
    }


    /**
     * @return the offset.
     */
    public int getOffset()
    {
        return offset;
    }


    /**
     * @return the limit.
     */
    public int getLimit()
    {
        return limit;
    }


    /**
     * @return the sortInfo.
     */
    public SortInfo getSortInfo()
    {
        return sortInfo;
    }


    /**
     * @return the total.
     */
    public int getTotal()
    {
        return total;
    }


    /**
     * Negative values are ignored.
     * 
     * @param total
     */
    public void setTotal(int total)
    {
        if (total < 0)
        {
            log.warning("Total must not be negative!");
            return;
        }
        this.total = total;
    }
}
