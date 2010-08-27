package name.pehl.taoki.rest.paging;

import name.pehl.taoki.rest.paging.parser.PageInfoParser;
import name.pehl.taoki.rest.paging.parser.QueryPageInfoParser;

import org.restlet.Request;

/**
 * An {@linkplain AbstractPagingResource paging resource} which uses query
 * parameter of the resource as input. The query must contain the following
 * fields
 * <ul>
 * <li><code>offset</code><br/>
 * The offset for the paging
 * <li><code>last-index</code><br/>
 * The last index of the result. The <code>last-index</code> is used to
 * calculate the limit of the {@link PageInfo} instance:
 * <code>limit = lastIndex - offsetValue + 1</code>
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction. Must match (case insensitiv) one of the constants in
 * {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>http://server/resource?offset=0&limit=50
 * <li>http://server/resource?offset=100&limit=50&sortField=surname
 * <li>http://server/resource?offset=1&limit=2&sortField=surname&sortDir=aSc
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          61416 $
 */
public abstract class PagingQueryResource extends AbstractPagingResource
{
    /**
     * Construct a new instance with a {@link QueryPageInfoParser}
     */
    public PagingQueryResource(PageInfoParser pip)
    {
        super(new QueryPageInfoParser());
    }


    /**
     * Returns the {@linkplain #getQuery() query}.
     * 
     * @param request
     * @return the {@linkplain #getQuery() query}.
     * @see name.pehl.taoki.rest.paging.AbstractPagingResource#getInput(org.restlet.Request)
     */
    @Override
    protected Object getInput(Request request)
    {
        return getQuery();
    }
}
