package name.pehl.taoki.security;

import com.google.inject.Injector;
import name.pehl.taoki.GuiceFinder;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;

/**
 * A {@link GuiceFinder} which invokes
 * {@link SecurityCheck#check(Request, Response)} before
 * {@link #handle(Request, Response)}.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class SecureFinder extends GuiceFinder
{
    public SecureFinder(Injector injector, Context context, Class<?> targetClass)
    {
        super(injector, context, targetClass);
    }


    @Override
    public void handle(Request request, Response response)
    {
        SecurityCheck securityCheck = injector.getInstance(SecurityCheck.class);
        if (securityCheck != null)
        {
            securityCheck.check(request, response);
        }
        super.handle(request, response);
    }
}
