package name.pehl.taoki.security;

import name.pehl.taoki.rest.GuiceFinder;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;

import com.google.inject.Injector;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class SecurityFinder extends GuiceFinder
{
    public SecurityFinder(Injector injector, Context context, Class<?> targetClass)
    {
        super(injector, context, targetClass);
    }
    
    
    @Override
    public void handle(Request request, Response response)
    {
        SecurityCheck securityCheck = new SecurityCheck(request);
        securityCheck.check();
        super.handle(request, response);
    }
}
