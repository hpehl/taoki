package name.pehl.taoki.rest.paging;

import static junit.framework.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import name.pehl.taoki.rest.IntegrationTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public abstract class NumbersIntegrationTest extends IntegrationTest
{
    @Override
    protected Map<String, Class<? extends ServerResource>> getRoutes()
    {
        Map<String, Class<? extends ServerResource>> routes = new HashMap<String, Class<? extends ServerResource>>();
        routes.put("/numbers", getResourceClass());
        return routes;
    }


    protected abstract Class<? extends ServerResource> getResourceClass();


    protected void assertJson(ClientResource resource) throws ResourceException, JSONException, IOException
    {
        JSONObject json = new JSONObject(resource.get().getText());
        assertNotNull(json);
        assertEquals(5, json.getInt("offset"));
        assertEquals(3, json.getInt("pages"));
        assertEquals(42, json.getInt("total"));
        assertEquals(new JSONArray(NumberFactory.numbers(5, 23)).toString(), json.getJSONArray("numbers").toString());
    }


    protected void assertError(ClientResource resource) throws ResourceException, JSONException, IOException
    {
        JSONObject json = new JSONObject(resource.get().getText());
        assertNotNull(json);
        assertEquals(NumbersResourceHelper.NO_PAGE_INFO, json.getString("error"));
    }
}
