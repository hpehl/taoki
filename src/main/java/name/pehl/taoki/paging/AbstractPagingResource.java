package name.pehl.taoki.paging;

import java.util.logging.Logger;

import javax.inject.Inject;

import name.pehl.taoki.paging.parser.PageInfoParseException;

/**
 * Abstract paging resource. This class must return a {@link PageInfo} instance
 * for the specified input.
 * 
 * @param <T>
 *            The input type
 * @author $Author$
 * @version $Date$ $Revision: 97
 *          $
 */
public abstract class AbstractPagingResource<T>
{
    @Inject
    Logger logger;


    /**
     * Responsible for returning a {@link PageInfo} instance for the specified
     * input.
     */
    protected abstract PageInfo getPageInfo(T input);


    protected int convertInt(String input, String message, Object... params) throws PageInfoParseException
    {
        try
        {
            int result = Integer.parseInt(input);
            return result;
        }
        catch (NumberFormatException e)
        {
            String error = String.format(message, params);
            throw new PageInfoParseException(error);
        }
    }
}
