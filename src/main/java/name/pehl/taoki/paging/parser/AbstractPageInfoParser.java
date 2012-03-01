package name.pehl.taoki.paging.parser;

/**
 * Contains common functionality for all {@link PageInfoParser} implementations.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 145
 *          $
 */
public abstract class AbstractPageInfoParser<T> implements PageInfoParser<T>
{
    /**
     * Verifies that the specified {@code clazz}
     * {@linkplain Class#isAssignableFrom(Class) is assignable from}
     * {@code input.getClass()}.
     * 
     * @param input
     * @param clazz
     * @throws PageInfoParseException
     */
    protected void verifyInput(Object input, Class<?> clazz) throws PageInfoParseException
    {
        if (input != null)
        {
            if (!clazz.isAssignableFrom(input.getClass()))
            {
                String error = String.format("Input has the wrong format. Expected: %s, given: %s", clazz.getName(),
                        input.getClass().getName());
                throw new PageInfoParseException(error);
            }
        }
    }


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
