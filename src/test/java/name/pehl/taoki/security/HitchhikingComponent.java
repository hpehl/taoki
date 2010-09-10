package name.pehl.taoki.security;

import name.pehl.taoki.TestComponent;

import org.restlet.Application;

import com.google.inject.Injector;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HitchhikingComponent extends TestComponent
{
    private Injector injector;


    public HitchhikingComponent(Injector injector)
    {
        super();
        this.injector = injector;
    }


    @Override
    protected void initialize()
    {
        if (application == null)
        {
            application = new Application();
        }
        if (router == null)
        {
            router = new SecureRouter(injector, application.getContext())
            {
                @Override
                protected void attachRoutes()
                {
                    // Routes will be attached later in the integration tests
                }
            };
        }
    }
}
