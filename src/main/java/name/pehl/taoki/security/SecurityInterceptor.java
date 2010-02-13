package name.pehl.taoki.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author $Author$
 * @version $Revision$
 */
public class SecurityInterceptor implements MethodInterceptor
{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null)
        {
            throw new SecurityException("No user");
        }

        ServerResource resource = (ServerResource) invocation.getThis();
        SecurityCheck securityCheck = new SecurityCheck(resource.getRequest());
        securityCheck.check();
        return invocation.proceed();
    }
}
