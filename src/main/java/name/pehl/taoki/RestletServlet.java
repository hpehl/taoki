package name.pehl.taoki;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServletAdapter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Abstract base class for a servlet which acts as a dispatcher for all
 * resources. Concrete subclasses have to override the method
 * {@link #createRouter(Injector, Context)} and provide a concrete
 * implementation of {@link GuiceRouter}:
 * 
 * <pre>
 * public class BookstoreRestletServlet extends RestletServlet
 * {
 *     protected GuiceRouter createRouter(final Injector injector, final Context context)
 *     {
 *         return new BookstoreGuiceRouter(injector, context);
 *     }
 * }
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$
 */
@Singleton
public abstract class RestletServlet extends HttpServlet
{
    @Inject
    protected Injector injector;
    protected ServletAdapter adapter;


    @Override
    public void init() throws ServletException
    {
        adapter = new ServletAdapter(getServletContext());
        Context context = adapter.getContext().createChildContext();
        Application application = new Application(context);
        application.setInboundRoot(createRouter(injector, context));
        adapter.setNext(application);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        adapter.service(request, response);
    }


    protected abstract GuiceRouter createRouter(final Injector injector, final Context context);
}
