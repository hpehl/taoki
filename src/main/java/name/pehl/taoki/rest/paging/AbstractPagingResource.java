package name.pehl.taoki.rest.paging;

import java.util.logging.Level;

import name.pehl.taoki.rest.paging.parser.PageInfoParseException;
import name.pehl.taoki.rest.paging.parser.PageInfoParser;

import org.restlet.Request;
import org.restlet.resource.ServerResource;

/**
 * Abstract paging resource which implements {@link HasPageInfo}. This class
 * creates a {@link PageInfo} instance using a specific {@link PageInfoParser}
 * (specified as constructor argument) based on a specific
 * {@linkplain #getInput(Request) input}.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class AbstractPagingResource extends ServerResource implements HasPageInfo
{
    private PageInfoParser pip;


    public AbstractPagingResource(PageInfoParser pip)
    {
        super();
        this.pip = pip;
    }


    /**
     * Returns an {@link PageInfo} instance using the methods
     * {@link #createPageInfoParser()} and {@link #getInput(Request)}. More
     * precisely the method does the following:
     * <ol>
     * <li>create a new {@link PageInfoParser} instance by calling
     * {@link #createPageInfoParser()}
     * <li>get the input by calling {@link #getInput()}
     * <li>create an instance of {@link PageInfo} by calling
     * {@link PageInfoParser#parse(Object)}
     * </ol>
     * <p>
     * If the input is invalid, an error message is logged.
     * 
     * @param request
     * @return the {@link PageInfo} instance for the input provided by
     *         {@link #getInput(Request)} or <code>null</code> if the input was
     *         null or invalid.
     * @see name.pehl.taoki.rest.paging.HasPageInfo#getPageInfo(org.restlet.Request)
     */
    @Override
    public PageInfo getPageInfo(Request request)
    {
        PageInfo pageInfo = null;
        if (pip != null)
        {
            Object input = getInput(request);
            try
            {
                pageInfo = pip.parse(input);
            }
            catch (PageInfoParseException e)
            {
                getLogger().log(Level.SEVERE, "Could not read page info from \"" + input + "\": " + e.getMessage(), e);
            }
        }
        return pageInfo;
    }


    /**
     * Used to provide the input for the {@link PageInfoParser}.
     * 
     * @param request
     * @return
     */
    protected abstract Object getInput(Request request);


    public PageInfoParser getPageInfoParser()
    {
        return pip;
    }
}
