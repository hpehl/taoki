package name.pehl.taoki.rest.paging;

import java.io.StringWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * @author $Author$
 * @version $Date$ $Revision: 135
 *          $
 */
public final class NumbersResourceHelper
{
    public static final String NO_PAGE_INFO = "No page info given";
    public static final String JSON_EXCEPTION = "JSONException";


    public static String numbersAsJson(PageInfo pageInfo)
    {
        StringWriter writer = new StringWriter();
        if (pageInfo == null)
        {
            writer.write("{\"error\":\"" + NO_PAGE_INFO + "\"}");
        }
        else
        {
            List<Integer> numbers = NumberFactory.numbers(42);
            PageResult<Integer> pageResult = new PageResult<Integer>(pageInfo, numbers);
            try
            {
                // @formatter:off
                new JSONWriter(writer).object()
                    .key("offset")
                    .value(pageResult.getPageInfo().getOffset())
                    .key("pages")
                    .value(pageResult.pages())
                    .key("total")
                    .value(pageResult.total())
                    .key("numbers")
                    .value(new JSONArray(pageResult.page()))
                .endObject();
                // @formatter:on
            }
            catch (JSONException e)
            {
                writer.write("{\"error\":\"" + JSON_EXCEPTION + "\"}");
            }
        }
        return writer.toString();
    }


    private NumbersResourceHelper()
    {
    }
}
