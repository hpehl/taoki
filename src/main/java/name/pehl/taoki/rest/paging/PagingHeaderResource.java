package name.pehl.taoki.rest.paging;

import name.pehl.taoki.rest.paging.parser.HeaderPageInfoParser;

import org.restlet.Request;
import org.restlet.data.Parameter;
import org.restlet.engine.http.header.HeaderConstants;
import org.restlet.util.Series;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses the custom
 * header <code>Item-Range</code> as input. The page info is expected in the
 * following format:
 * 
 * <pre>
 * Item-Range: items={offset}-{last-index}[;{sortField}[:{sortDir}]]
 * </pre>
 * <ul>
 * <li><code>offset</code><br/>
 * The offset in the result.
 * <li><code>last-index</code><br/>
 * The last index of the result. The <code>last-index</code> is used to
 * calculate the size of one page:
 * <code>pageSize = lastIndex - offsetValue + 1</code>
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction. Must match (case insensitiv) one of the constants in
 * {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>Item-Range: items=0-24
 * <li>Item-Range: items=25-49;surname
 * <li>Item-Range: items=100-124;createdAt:dEsC
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public abstract class PagingHeaderResource extends AbstractPagingResource
{
    /**
     * The name of the custom header carrying the item range data.
     */
    public static final String ITEM_RANGE_HEADER = "Item-Range";


    /**
     * Construct a new instance with a {@link HeaderPageInfoParser}
     */
    public PagingHeaderResource()
    {
        super(new HeaderPageInfoParser());
    }


    /**
     * Returns the value of the custom <code>Item-Range</code> header, or
     * <code>null</code> if no such header was found.
     * 
     * @param request
     * @return the value of the custom <code>Item-Range</code> header, or
     *         <code>null</code> if no such header was found.
     * @see name.pehl.taoki.rest.paging.AbstractPagingResource#getInput(org.restlet.Request)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Object getInput(Request request)
    {
        String itemRangeHeader = null;
        Series<Parameter> header = (Series<Parameter>) request.getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (header != null)
        {
            itemRangeHeader = header.getFirstValue(ITEM_RANGE_HEADER);
        }
        return itemRangeHeader;
    }
}
