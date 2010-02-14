package name.pehl.taoki.security;

import name.pehl.taoki.rest.GuiceRouter;

import org.restlet.Context;
import org.restlet.resource.Finder;

import com.google.inject.Injector;

/**
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
