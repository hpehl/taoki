package name.pehl.taoki.paging;

import name.pehl.taoki.TestComponent;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.Form;
import org.restlet.engine.http.header.HeaderConstants;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.IOException;

/**
 * @author $Author$
 * @version $Date$ $Revision: 135
 *          $
 */
@Ignore
public class NumbersHeaderResourceIntegrationTest extends NumbersIntegrationTest
{
    @Override
    protected Class<? extends ServerResource> getResourceClass()
    {
        return NumbersHeaderResource.class;
    }


    @Test
    public void testNoPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(TestComponent.getBaseUrl() + "/numbers");
        assertError(resource);
    }


    @Test
    public void testInvalidPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(TestComponent.getBaseUrl() + "/numbers");
        resource.getRequest().getAttributes()
                .put(HeaderConstants.ATTRIBUTE_HEADERS, new Form(PagingHeaderResource.ITEM_RANGE_HEADER + "=foo"));
        assertError(resource);
    }


    @Test
    public void testPaging() throws IOException, ResourceException, JSONException
    {
        ClientResource resource = new ClientResource(TestComponent.getBaseUrl() + "/numbers");
        resource.getRequest()
                .getAttributes()
                .put(HeaderConstants.ATTRIBUTE_HEADERS,
                        new Form(PagingHeaderResource.ITEM_RANGE_HEADER + "=items=5-23"));
        assertJson(resource);
    }
}
