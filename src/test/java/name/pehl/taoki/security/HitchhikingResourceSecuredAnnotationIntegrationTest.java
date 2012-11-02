package name.pehl.taoki.security;

import com.google.inject.AbstractModule;
import org.junit.Ignore;
import org.restlet.resource.ServerResource;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;

/**
 * @author $Author$
 * @version $Date$ $Revision: 149
 *          $
 */
@Ignore
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
