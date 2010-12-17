package name.pehl.taoki.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import name.pehl.taoki.TestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

/**
 * @author $Author$
 * @version $Date$ $Revision: 150
 *          $
 */
public abstract class HitchhikingIntegrationTest
{
    private HitchhikingComponent component;


    @Before
    public void setUp() throws Exception
    {
        component = new HitchhikingComponent(Guice.createInjector(getModule()));
        component.startWith("/hitchhiking", getResourceClass());
    }


    @After
    public void tearDown() throws Exception
    {
        if (component != null)
        {
            component.stop();
        }
    }


    protected abstract AbstractModule getModule();


    protected abstract Class<? extends ServerResource> getResourceClass();


    @Test
    public void getNoAnswer() throws ResourceException, IOException
    {
        ClientResource clientResource = new ClientResource(TestComponent.getBaseUrl() + "/hitchhiking");
        try
        {
            clientResource.get();
            fail("ResourceException expected");
        }
        catch (ResourceException e)
        {
            Status status = e.getStatus();
            assertEquals(Status.CLIENT_ERROR_FORBIDDEN, status);
        }
    }


    @Test
    public void getWrongAnswer() throws ResourceException, IOException
    {
        ClientResource clientResource = new ClientResource(TestComponent.getBaseUrl() + "/hitchhiking?answer=41");
        try
        {
            clientResource.get();
            fail("ResourceException expected");
        }
        catch (ResourceException e)
        {
            Status status = e.getStatus();
            assertEquals(Status.CLIENT_ERROR_FORBIDDEN, status);
        }
    }


    @Test
    public void getAnswer() throws ResourceException, IOException
    {
        ClientResource clientResource = new ClientResource(TestComponent.getBaseUrl() + "/hitchhiking?answer=42");
        String text = clientResource.get().getText();
        assertEquals("Don't panic", text);
    }
}
