package name.pehl.taoki.security;

import name.pehl.taoki.rest.GuiceRouter;

import org.restlet.Context;
import org.restlet.resource.Finder;

import com.google.inject.Injector;



/**
 * @author $LastChangedBy:$ 
 * @version $LastChangedRevision:$ 
 */
public abstract class SecurityRouter extends GuiceRouter
{
    public SecurityRouter(Injector injector, Context context)
    {
        super(injector, context);
    }

    
    
    @Override
    public Finder createFinder(Class<?> targetClass)
    {
        return new SecurityFinder(injector, getContext(), targetClass);
    }
}
