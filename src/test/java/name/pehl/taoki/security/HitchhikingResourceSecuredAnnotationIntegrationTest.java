package name.pehl.taoki.security;

import static com.google.inject.matcher.Matchers.*;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author$
 * @version $Date$ $Revision: 149
 *          $
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
                SecurityInterceptor interceptor = new SecurityInterceptor();
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
