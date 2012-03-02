package name.pehl.taoki;

import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import com.google.inject.Injector;

/**
 * Abstract base class for a router which uses a {@link GuiceFinder} to create
 * resources. Concrete subclasses have to override the method
 * {@link #attachRoutes()} and map the urls to resources:
 * 
 * <pre>
 * public class BookstoreRouter extends GuiceRouter
 * {
 *     public BookstoreRouter(Injector injector, Context context)
 *     {
 *         super(injector, context);
 *     }
 * 
 * 
 *     protected void attachRoutes()
 *     {
 *         attach(&quot;/books&quot;, BooksResource.class);
 *     }
 * }
 * </pre>
 * <p>
 * See {@link name.pehl.taoki.RestletServlet RestletServlet} for further
 * details.
 * 
 * @author $Author$
 * @version $Revision$
 */
public abstract class GuiceRouter extends Router
{
    protected final Injector injector;


    public GuiceRouter(Injector injector, Context context)
    {
        super(context);
        this.injector = injector;
        attachRoutes();
    }


    @Override
    public Finder createFinder(Class<? extends ServerResource> targetClass)
    {
        return new GuiceFinder(injector, getContext(), targetClass);
    }


    protected abstract void attachRoutes();


    protected Injector getInjector()
    {
        return injector;
    }
}
