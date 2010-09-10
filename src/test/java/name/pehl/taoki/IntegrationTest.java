package name.pehl.taoki;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

/**
 * Base class for all integration tests. Starts a standalone http server on
 * localhost at port {@link #PORT}.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 135
 *          $
 */
public abstract class IntegrationTest
{
    public static final int DEFAULT_PORT = 1337;
    public static final String PORT_SYSTEM_PROPERTY = "taoki.test.port";
    public static int PORT = getTestPort();
    public static final String BASE_URL = "http://localhost:" + PORT;

    protected Component component;


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


    /**
     * Starts a http server on localhost at port {@link #PORT} and attaches the
     * routes from {@link #getRoutes()}.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        component = new Component();
        component.getServers().add(Protocol.HTTP, PORT);
        Application application = new Application();
        Router router = new Router();
        Map<String, Class<? extends ServerResource>> routes = getRoutes();
        if (routes != null)
        {
            for (Map.Entry<String, Class<? extends ServerResource>> entry : routes.entrySet())
            {
                router.attach(entry.getKey(), entry.getValue());
            }
        }
        application.setInboundRoot(router);
        component.getDefaultHost().attach(application);
        component.start();
    }


    /**
     * Stops the server started in {@link #setUp()}.
     * 
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
        if (component != null)
        {
            component.stop();
        }
    }


    /**
     * Must return the urls and attached resources.
     * 
     * @return
     */
    protected abstract Map<String, Class<? extends ServerResource>> getRoutes();
}
