package name.pehl.taoki.rest.paging;

import static name.pehl.taoki.rest.paging.NumbersResourceHelper.*;

import org.json.JSONException;
import org.restlet.resource.Get;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class NumbersHeaderResource extends PagingHeaderResource
{
    @Get("json")
    public String represent() throws JSONException
    {
        return numbersAsJson(getPageInfo());
    }
}
