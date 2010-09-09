package name.pehl.taoki.rest.paging;

import static name.pehl.taoki.rest.paging.NumbersResourceHelper.numbersAsJson;

import org.json.JSONException;
import org.restlet.resource.Get;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-09 17:57:49 +0200 (Do, 09. Sep 2010) $ $Revision: 135
 *          $
 */
public class NumbersUrlResource extends PagingUrlResource
{
    @Get("json")
    public String represent() throws JSONException
    {
        return numbersAsJson(getPageInfo());
    }
}
