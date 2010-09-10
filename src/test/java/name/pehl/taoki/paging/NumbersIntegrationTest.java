package name.pehl.taoki.paging;

import static junit.framework.Assert.*;

import java.io.IOException;

import name.pehl.taoki.TestComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public abstract class NumbersIntegrationTest
{
    protected TestComponent component;


    @Before
    public void setUp() throws Exception
    {
        component = new TestComponent();
        component.startWith("/numbers", getResourceClass());
    }


    @After
    public void tearDown() throws Exception
    {
        if (component != null)
        {
            component.stop();
        }
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
