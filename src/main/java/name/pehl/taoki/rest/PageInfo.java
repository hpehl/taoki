package name.pehl.taoki.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Klasse, die für das Blättern in großen Datenmengen verwendet wird. Der
 * Konstruktor überprüft die übergegebenen Argumente auf Gültigkeit:
 * <ol>
 * <li><code>offset &gt; MIN_OFFSET && offset &lt; MAX_OFFSET</code></li>
 * <li><code>limit &gt; MIN_LIMIT && limit &lt; MAX_LIMIT</code></li>
 * </ol>
 * Falls die Parameter <code>offset</code> und <code>limit</code> außerhalb des
 * gültigen Wertebereich liegen, werden Sie mit den Minimal- bzw. Maximalwert
 * initialisiert.
 * </p>
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
     * Construct a new instance of this class. Die Parameter
     * 
     * @param limit
     * @param offset
     * @param sortInfo
     */
    public PageInfo(int offset, int limit)
    {
        this(offset, limit, new SortInfo());
    }


    /**
     * Construct a new instance of this class
     * 
     * @param limit
     * @param offset
     * @param sortInfo
     */
    public PageInfo(int offset, int limit, SortInfo sortInfo)
    {
        super();
        if (offset < MIN_OFFSET)
        {
            this.offset = MIN_OFFSET;
            log.warning("Der Offset '" + offset + "' in der PageInfo ist kleiner als der minimale Offset '" + MIN_OFFSET
                    + "'. Setze den Offset auf das Minimum.");
        }
        else if (offset > MAX_OFFSET)
        {
            this.offset = MAX_OFFSET;
            log.warning("Der Offset '" + offset + "' in der PageInfo ist groesser als der maximale Offset '" + MAX_OFFSET
                    + "'. Setze den Offset auf das Maximum.");
        }
        else
        {
            this.offset = offset;
        }
        if (limit < MIN_LIMIT)
        {
            this.limit = MIN_LIMIT;
            log.warning("Das Limit '" + limit + "' in der PageInfo ist kleiner als der minimale Limit '" + MIN_LIMIT
                    + "'. Setze das Limit auf das Minimum.");
        }
        else if (limit > MAX_LIMIT)
        {
            this.limit = MAX_LIMIT;
            log.warning("Das Limit '" + limit + "' in der PageInfo ist groesser als das maximale Limit '" + MAX_LIMIT
                    + "'. Setze das Limit auf das Maximum.");
        }
        else
        {
            this.limit = limit;
        }
        this.sortInfo = sortInfo;
        this.total = 0;
    }


    /**
     * Liefert eine Sublist der angegebenen Liste, die den Paging Werten dieser
     * Instanz entsprechen.
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
     * Basiert auf {@code offset}, {@code limit} und {@code sortInfo}
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
     * Basiert auf {@code offset}, {@code limit} und {@code sortInfo}
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
        if (total != other.total)
        {
            return false;
        }
        return true;
    }


    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append(offset).append(limit).append(total).append(sortInfo.getSortField())
                .append(sortInfo.getSortDir().name().toLowerCase()).toString();
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


    public void setTotal(int total)
    {
        this.total = total;
    }
}
