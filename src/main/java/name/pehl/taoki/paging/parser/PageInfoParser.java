package name.pehl.taoki.paging.parser;

import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.PageInfoParseException;

/**
 * Interface for parsing and creating an instance of {@link PageInfo} out of an
 * input.
 * 
 * @param <T>
 *            The input type
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public interface PageInfoParser<T>
{
    String OFFSET = "offset";
    String PAGE_SIZE = "pageSize";


    /**
     * Creates an instance of {@link PageInfo} from the specified input. Returns
     * <code>null</code>, if the input was <code>null</code>. Throws a
     * {@link PageInfoParseException} if the input was not <code>null</code> but
     * invalid.
     * 
     * @param input
     *            The input containing all necessary data to create an
     *            {@link PageInfo} instance.
     * @return An {@link PageInfo} instance with the data from
     *         <code>input</code> or <code>null</code> if the input was
     *         <code>null</code>.
     * @throws PageInfoParseException
     *             if the input was not <code>null</code> but invalid.
     */
    PageInfo parse(T input) throws PageInfoParseException;
}
