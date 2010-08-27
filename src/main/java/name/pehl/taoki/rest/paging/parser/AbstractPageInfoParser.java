package name.pehl.taoki.rest.paging.parser;

import name.pehl.taoki.rest.paging.SortDir;

/**
 * Contains common functionality for all {@link PageInfoParser} implementations.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class AbstractPageInfoParser implements PageInfoParser
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


    /**
     * Converts the specifed string to a {@link SortDir} constant.
     * 
     * @param input
     *            the {@link SortDir} constant to convert (case insensitiv!)
     * @return {@link SortDir#NONE} if <code>input</code> is <code>null</code>,
     *         the relevant {@link SortDir} constant otherwise.
     * @throws PageInfoParseException
     *             if <code>input</code> is not <code>null</code>, but invalid.
     */
    protected SortDir convertSortDir(String input) throws PageInfoParseException
    {
        if (input == null)
        {
            return SortDir.NONE;
        }

        try
        {
            SortDir result = SortDir.valueOf(input.toUpperCase());
            return result;
        }
        catch (IllegalArgumentException iae)
        {
            String error = String
                    .format("Page info contains the invalid sort direction \"%s\". Please specify one of the following constants: %s",
                            input, SortDir.values());
            throw new PageInfoParseException(error);
        }
    }
}
