package name.pehl.taoki.security;

import static com.google.inject.matcher.Matchers.*;

import org.aopalliance.intercept.MethodInvocation;
import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HitchhikingResourceSecuredAnnotationIntegrationTest extends HitchhikingIntegrationTest
{
    @Override
    protected AbstractModule getModule()
    {
        return new AbstractModule()
        {
            @Override
            protected void configure()
            {
                SecurityInterceptor interceptor = new SecurityInterceptor()
                {
                    @Override
                    public Object invoke(MethodInvocation invocation) throws Throwable
                    {
                        // Skip UserService check as it isn't available in
                        // integration test
                        ServerResource resource = (ServerResource) invocation.getThis();
                        getSecurityCheck().check(resource.getRequest(), resource.getResponse());
                        return invocation.proceed();
                    }
                };
                requestInjection(interceptor);
                bindInterceptor(subclassesOf(ServerResource.class), annotatedWith(Secured.class), interceptor);
                bind(SecurityCheck.class).to(TheAnswerCheck.class);
                bind(HitchhikingResourceSecuredAnnotation.class);
            }
        };
    }


    @Override
    protected Class<? extends ServerResource> getResourceClass()
    {
        return HitchhikingResourceSecuredAnnotation.class;
    }
}
