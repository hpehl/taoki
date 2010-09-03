package name.pehl.taoki.rest.paging;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Immutable value object for paging over large data. The class holds the
 * following information:
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

    private final int offset;
    private final int limit;
    private final SortInfo sortInfo;


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
     * <code>PageInfo[&lt;offset&gt;/&lt;limit&gt;/&lt;sortInfo&gt;]</code>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder("PageInfo [").append(offset).append("/").append(limit).append("/").append(sortInfo)
                .append("]").toString();
    }


    /**
     * Creates a new {@link PageInfo} instance with the offset for the previous
     * page.
     * 
     * @return the previous {@link PageInfo}
     */
    public PageInfo previous()
    {
        return new PageInfo(getOffset() - limit, getLimit());
    }


    /**
     * Returns the next {@link PageInfo} instance.
     * 
     * @return
     */
    public PageInfo next()
    {
        return new PageInfo(getOffset() + limit, getLimit());
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
}
