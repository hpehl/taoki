package name.pehl.taoki.paging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-09 17:57:49 +0200 (Do, 09. Sep 2010) $ $Revision: 135
 *          $
 */
public class NumbersUrlResourceIntegrationTest extends NumbersIntegrationTest
{
    @Override
    protected Map<String, Class<? extends ServerResource>> getRoutes()
    {
        Map<String, Class<? extends ServerResource>> routes = new HashMap<String, Class<? extends ServerResource>>();
        routes.put("/numbers", getResourceClass());
        routes.put("/numbers/{offset}/{pageSize}", getResourceClass());
        return routes;
    }


    @Override
    protected Class<? extends ServerResource> getResourceClass()
    {
        return NumbersUrlResource.class;
    }


    @Test
    public void testNoPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(BASE_URL + "/numbers");
        assertError(resource);
    }


    @Test
    public void testInvalidPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(BASE_URL + "/numbers/foo/bar");
        assertError(resource);
    }


    @Test
    public void testPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(BASE_URL + "/numbers/5/19");
        assertJson(resource);
    }
}
