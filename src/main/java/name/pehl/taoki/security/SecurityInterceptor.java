package name.pehl.taoki.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Revision$
 */
public class SecurityInterceptor implements MethodInterceptor
{
    private SecurityCheck securityCheck;


    @Inject
    public void setSecurityCheck(SecurityCheck securityCheck)
    {
        this.securityCheck = securityCheck;
    }


    protected SecurityCheck getSecurityCheck()
    {
        return securityCheck;
    }


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
        securityCheck.check(resource.getRequest(), resource.getResponse());
        return invocation.proceed();
    }
}
