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
        SecurityInterceptor interceptor = new SecurityInterceptor();
        bindInterceptor(subclassesOf(ServerResource.class), annotatedWith(Secured.class), interceptor);
    }
}
