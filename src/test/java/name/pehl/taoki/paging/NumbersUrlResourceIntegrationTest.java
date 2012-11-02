package name.pehl.taoki.paging;

import name.pehl.taoki.TestComponent;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.IOException;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-09 17:57:49 +0200 (Do, 09. Sep 2010) $ $Revision: 135
 *          $
 */
@Ignore
public class NumbersUrlResourceIntegrationTest extends NumbersIntegrationTest
{
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        component.attach("/numbers/{offset}/{pageSize}", getResourceClass());
    }


    @Override
    protected Class<? extends ServerResource> getResourceClass()
    {
        return NumbersUrlResource.class;
    }


    @Test
    public void testInvalidPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(TestComponent.getBaseUrl() + "/numbers/foo/bar");
        assertError(resource);
    }


    @Test
    public void testPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(TestComponent.getBaseUrl() + "/numbers/5/19");
        assertJson(resource);
    }
}
