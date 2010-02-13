package name.pehl.taoki.security;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author$
 * @version $Revision$
 */
public class SecurityModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(CookieSecurityCheck.class).to(CookieSecurityCheck.class);
        SecurityInterceptor interceptor = new SecurityInterceptor();
        requestInjection(interceptor);
        bindInterceptor(subclassesOf(ServerResource.class), annotatedWith(Secured.class), interceptor);
    }
}
