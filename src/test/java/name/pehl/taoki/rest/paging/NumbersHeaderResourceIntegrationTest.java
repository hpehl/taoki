package name.pehl.taoki.rest.paging;

import static junit.framework.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import name.pehl.taoki.rest.IntegrationTestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ServerResource;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class NumbersHeaderResourceIntegrationTest extends IntegrationTestCase
{
    @Override
    protected Map<String, Class<? extends ServerResource>> getRoutes()
    {
        Map<String, Class<? extends ServerResource>> routes = new HashMap<String, Class<? extends ServerResource>>();
        routes.put("/numbers", NumbersHeaderResource.class);
        return routes;
    }


    @Test
    @Ignore
    public void testRepresent() throws IOException
    {
        ClientResource resource = new ClientResource(BASE_URL + "/numbers");
        Representation representation = resource.get();
        assertNotNull(representation);
        String json = representation.getText();
        assertNotNull(json);
    }
}
