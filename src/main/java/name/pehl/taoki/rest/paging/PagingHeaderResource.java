package name.pehl.taoki.rest.paging;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.resource.ServerResource;

/**
 * Uses the 'Range' header:
 * 
 * <pre>
 * Range: items=0-24/name/asc
 * </pre>
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class PagingHeaderResource extends ServerResource implements HasPageInfo
{
    private static final String RANGE_REGEXP = "^items=([0-9]+)-([0-9]+)(/([\\w]+)/(asc|desc))?";
    
    /**
     * @param request
     * @return
     * @see name.pehl.taoki.rest.paging.HasPageInfo#getPageInfo(org.restlet.Request)
     */
    @Override
    public PageInfo getPageInfo(Request request)
    {
        PageInfo pageInfo = null;
        Object object = request.getAttributes().get("org.restlet.http.headers");
        if (object != null && object instanceof Form)
        {
            Form headers = (Form) object;
            String range = headers.getFirstValue("Range");
            pageInfo = parseRange(range);
        }
        return pageInfo;
    }


    /**
     * @param range
     * @return
     */
    protected PageInfo parseRange(String range)
    {
        PageInfo pageInfo = null;
        if (range != null && range.length() != 0)
        {
            String items = "items=";
            if (items.startsWith(items))
            {
                String offset = items.substring(items.length());
            }
        }
        return pageInfo;
    }
}
