package name.pehl.taoki.rest.paging;

import java.io.StringWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public final class NumbersResourceHelper
{
    public static String numbersAsJson(PageInfo pageInfo)
    {
        List<Integer> numbers = NumberFactory.numbers(42);
        PageResult<Integer> pageResult = new PageResult<Integer>(pageInfo, numbers);
        StringWriter writer = new StringWriter();
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
                .array()
                    .value(new JSONArray(pageResult.page()))
                .endArray()
            .endObject();
            // @formatter:on
        }
        catch (JSONException e)
        {
            writer.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
        return writer.toString();
    }


    private NumbersResourceHelper()
    {
    }
}
