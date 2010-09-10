package name.pehl.taoki;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * A finder which uses the specified {@link Injector} to create resources. The
 * resources are created using <code>injector.getInstance(targetClass)</code>.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class GuiceFinder extends Finder
{
    protected final Injector injector;


    public GuiceFinder(Injector injector, Context context, Class<?> targetClass)
    {
        super(context, targetClass);
        this.injector = injector;
    }


    @Override
    public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response)
    {
        return injector.getInstance(targetClass);
    }
}
