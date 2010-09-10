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
    public static final int DEFAULT_PORT = 1337;
    public static final String PORT_SYSTEM_PROPERTY = "taoki.test.port";
    public static int PORT = getTestPort();
    public static final String BASE_URL = "http://localhost:" + PORT;

    protected Application application;
    protected Router router;


    public TestComponent()
    {
        super();
        getServers().add(Protocol.HTTP, PORT);
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


    /**
     * Returns the port for the standalone server. The port defaults to
     * {@value #DEFAULT_PORT} and can be overwritten by the system property
     * {@value #PORT_SYSTEM_PROPERTY}.
     * 
     * @return
     */
    private static int getTestPort()
    {
        if (System.getProperties().containsKey(PORT_SYSTEM_PROPERTY))
        {
            return Integer.parseInt(System.getProperty(PORT_SYSTEM_PROPERTY));
        }
        return DEFAULT_PORT;
    }
}
