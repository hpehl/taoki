package name.pehl.taoki.paging.parser;

import name.pehl.taoki.paging.PageInfo;

/**
 * Interface for parsing and creating an instance of {@link PageInfo} out of an
 * input.
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          85318 $
 */
public interface PageInfoParser
{
    String OFFSET = "offset";
    String PAGE_SIZE = "pageSize";
    String SORT_FIELD = "sortField";
    String SORT_DIR = "sortDir";


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
    PageInfo parse(Object input) throws PageInfoParseException;
}
