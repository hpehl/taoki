package name.pehl.taoki.paging;

import static name.pehl.taoki.paging.NumbersResourceHelper.*;

import name.pehl.taoki.paging.PagingHeaderResource;

import org.json.JSONException;
import org.restlet.resource.Get;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class NumbersHeaderResource extends PagingHeaderResource
{
    @Get("json")
    public String represent() throws JSONException
    {
        return numbersAsJson(getPageInfo());
    }
}
