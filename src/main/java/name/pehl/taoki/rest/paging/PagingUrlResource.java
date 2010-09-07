package name.pehl.taoki.rest.paging;

import name.pehl.taoki.rest.paging.parser.PageInfoParser;
import name.pehl.taoki.rest.paging.parser.UrlPageInfoParser;

import org.restlet.Request;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses the url of
 * the resource as input. The url must contain the following template
 * parameters:
 * <ul>
 * <li><code>offset</code><br/>
 * The offset in the result.
 * <li><code>pageSize</code><br/>
 * The size of one page. 
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction. Must match (case insensitiv) one of the constants in
 * {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>http://server/resource/0/50
 * <li>http://server/resource/10/20/none
 * <li>http://server/resource/100/50/surname
 * <li>http://server/resource/1/2/surname/asc
 * <li>http://server/resource/1/2/zipCode/dESc
 * </ul>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-21 11:32:14 +0100 (Mi, 21 Jan 2009) $ $Revision:
 *          61416 $
 */
public abstract class PagingUrlResource extends AbstractPagingResource
{
    /**
     * Construct a new instance with a {@link UrlPageInfoParser}
     */
    public PagingUrlResource(PageInfoParser pip)
    {
        super(new UrlPageInfoParser());
    }


    /**
     * Returns the {@linkplain Request#getAttributes() url template parameter}.
     * 
     * @param request
     * @return the {@linkplain Request#getAttributes() url template parameter}.
     * @see name.pehl.taoki.rest.paging.AbstractPagingResource#getInput(org.restlet.Request)
     */
    @Override
    protected Object getInput(Request request)
    {
        return request.getAttributes();
    }
}
