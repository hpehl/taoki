package name.pehl.taoki.rest.paging;

import name.pehl.taoki.rest.paging.parser.HeaderPageInfoParser;

import org.restlet.Request;
import org.restlet.data.Form;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses the <a
 * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35" >Range
 * Header</a> as input. The page info is expected in the following format:
 * 
 * <pre>
 * Range: items={offset}-{last-index}[/{sortField}[/{sortDir}]]
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
 * <li>Range: items=0-24
 * <li>Range: items=25-49/surname
 * <li>Range: items=100-124/createdAt/dEsC
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public class PagingHeaderResource extends AbstractPagingResource
{
    /**
     * Construct a new instance with a {@link HeaderPageInfoParser}
     */
    public PagingHeaderResource()
    {
        super(new HeaderPageInfoParser());
    }


    /**
     * Returns the <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35"
     * >Range Header</a>.
     * 
     * @param request
     * @return The range header or <code>null</code> if no header was specified.
     * @see name.pehl.taoki.rest.paging.AbstractPagingResource#getInput(org.restlet.Request)
     */
    @Override
    protected Object getInput(Request request)
    {
        String rangeHeader = null;
        Object object = request.getAttributes().get("org.restlet.http.headers");
        if (object != null && object instanceof Form)
        {
            Form headers = (Form) object;
            rangeHeader = headers.getFirstValue("Range");
        }
        return rangeHeader;
    }
}
