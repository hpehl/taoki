package name.pehl.taoki.security;

import name.pehl.taoki.GuiceRouter;

import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * A {@link GuiceRouter} which uses a {@link SecureFinder}.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class SecureRouter extends GuiceRouter
{
    public SecureRouter(Injector injector, Context context)
    {
        super(injector, context);
    }


    @Override
    public Finder createFinder(Class<?> targetClass)
    {
        return new SecureFinder(injector, getContext(), targetClass);
    }
}
