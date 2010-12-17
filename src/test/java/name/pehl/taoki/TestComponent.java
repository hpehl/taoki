package name.pehl.taoki;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

/**
 * @author $Author$
 * @version $Date$ $Revision: 149
 *          $
 */
public class TestComponent extends Component
{
    public static final int STARTING_PORT = 10000;
    private static int effectivePort = STARTING_PORT;

    protected Application application;
    protected Router router;


    public TestComponent()
    {
        super();
        effectivePort++;
        getServers().add(Protocol.HTTP, effectivePort);
    }


    protected void initialize()
    {
        if (application == null)
        {
            application = new Application();
        }
        if (router == null)
        {
            router = new Router();
        }
    }


    public void startWith(String path, Class<? extends ServerResource> target) throws Exception
    {
        attach(path, target);
        application.setInboundRoot(router);
        getDefaultHost().attach(application);
        start();
    }


    public void attach(String path, Class<? extends ServerResource> target)
    {
        initialize();
        router.attach(path, target);
    }


    public static String getBaseUrl()
    {
        return "http://localhost:" + effectivePort;
    }
}
