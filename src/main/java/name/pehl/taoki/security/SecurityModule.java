package name.pehl.taoki.security;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author $Author$
 * @version $Revision$
 */
public class SecurityModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        SecurityInterceptor interceptor = new SecurityInterceptor();
        bindInterceptor(Matchers.subclassesOf(ServerResource.class), Matchers.annotatedWith(Secured.class), interceptor);
    }
}
