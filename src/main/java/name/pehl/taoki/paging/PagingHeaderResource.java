package name.pehl.taoki.paging;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

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
public abstract class PagingHeaderResource extends AbstractPagingResource<HttpHeaders>
{
    /**
     * The name of the custom header carrying the item range data.
     */
    public static final String ITEM_RANGE_HEADER = "Item-Range";
    private static final String REGEXP = "^items=([0-9]+)-([0-9]+)";


    @Override
    protected PageInfo getPageInfo(HttpHeaders input)
    {
        PageInfo result = null;
        if (input != null)
        {
            List<String> requestHeader = input.getRequestHeader(ITEM_RANGE_HEADER);
            if (requestHeader != null && !requestHeader.isEmpty())
            {
                String headerValue = requestHeader.get(0);
                Pattern p = Pattern.compile(REGEXP);
                Matcher m = p.matcher(headerValue);

                String offset = null;
                int offsetValue = 0;
                String lastIndex = null;
                int lastIndexValue = 0;
                if (m.matches() && m.groupCount() > 1)
                {
                    offset = m.group(1);
                    lastIndex = m.group(2);
                    try
                    {
                        offsetValue = Integer.parseInt(offset);
                    }
                    catch (NumberFormatException e)
                    {
                        logger.log(Level.SEVERE, String.format(
                                "Paging info \"%s\" contains the invalid offset: \"%s\"", headerValue, offset));
                    }
                    try
                    {
                        lastIndexValue = Integer.parseInt(lastIndex);
                    }
                    catch (NumberFormatException e)
                    {
                        logger.log(Level.SEVERE, String.format(
                                "Paging info \"%s\" contains the invalid last index: \"%s\"", headerValue, lastIndex));
                    }
                    int pageSize = lastIndexValue - offsetValue + 1;
                    result = new PageInfo(offsetValue, pageSize);
                }
                else
                {
                    logger.log(Level.SEVERE, String.format(
                            "Paging info has the wrong format. Expected: %s, given: \"%s\"", REGEXP, headerValue));
                }
            }
            else
            {
                logger.log(Level.SEVERE, "No request header \"" + ITEM_RANGE_HEADER + "\" found");
            }
        }
        else
        {
            logger.log(Level.SEVERE, "No input specified");
        }
        return result;
    }
}
