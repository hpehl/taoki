package name.pehl.taoki.rest.paging;

import java.io.Serializable;

/**
 * @author $Author: lfstad-bauerb $
 * @version $Date: 2009-01-14 13:56:25 +0100 (Mi, 14 Jan 2009) $ $Revision:
 *          61016 $
 */
public class SortInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String sortField;
    private SortDir sortDir = SortDir.NONE;


    /**
     * Construct a new instance with no sort field (<code>null</code>) and
     * {@link SortDir#NONE} as sort direction.
     */
    public SortInfo()
    {
        this(null, SortDir.NONE);
    }


    /**
     * Construct a new instance with the specified sort field and
     * {@link SortDir#NONE} as sort direction.
     * 
     * @param sortField
     */
    public SortInfo(String sortField)
    {
        this(sortField, SortDir.NONE);
    }


    /**
     * Construct a new instance with the specified sort field and direction.
     * 
     * @param sortDir
     * @param sortField
     */
    public SortInfo(String sortField, SortDir sortDir)
    {
        super();
        this.sortField = sortField;
        this.sortDir = sortDir;
    }


    /**
     * Based on {@code sortField} and {@code sortDir}
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sortField == null) ? 0 : sortField.hashCode());
        result = prime * result + ((sortDir == null) ? 0 : sortDir.hashCode());
        return result;
    }


    /**
     * Based on {@code sortField} and {@code sortDir}
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
        SortInfo other = (SortInfo) obj;
        if (sortField == null)
        {
            if (other.sortField != null)
            {
                return false;
            }
        }
        else if (!sortField.equals(other.sortField))
        {
            return false;
        }
        if (sortDir == null)
        {
            if (other.sortDir != null)
            {
                return false;
            }
        }
        else if (!sortDir.equals(other.sortDir))
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
        return new StringBuilder().append(sortField).append("/").append(sortDir).toString();
    }


    /**
     * @return <code>true</code> if
     *         <code>sortField != null && sortDir != SortDir.NONE</code>,
     *         <code>false</code> otherwise.
     */
    public boolean shouldSort()
    {
        return sortField != null && sortDir != SortDir.NONE;
    }


    /**
     * @return the sortField.
     */
    public String getSortField()
    {
        return sortField;
    }


    /**
     * @return the sortDir.
     */
    public SortDir getSortDir()
    {
        return sortDir;
    }
}
