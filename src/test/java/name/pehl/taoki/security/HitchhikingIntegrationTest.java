package name.pehl.taoki.security;

import static org.junit.Assert.*;

import java.io.IOException;

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
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class HitchhikingIntegrationTest
{
    private HitchhikingComponent securityComponent;


    @Before
    public void setUp() throws Exception
    {
        securityComponent = new HitchhikingComponent(Guice.createInjector(getModule()));
        securityComponent.startWith("/hitchhiking", getResourceClass());
    }


    protected abstract AbstractModule getModule();


    protected abstract Class<? extends ServerResource> getResourceClass();


    @After
    public void tearDown() throws Exception
    {
        if (securityComponent != null)
        {
            securityComponent.stop();
        }
    }


    @Test
    public void getNoAnswer() throws ResourceException, IOException
    {
        ClientResource clientResource = new ClientResource(HitchhikingComponent.BASE_URL + "/hitchhiking");
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
        ClientResource clientResource = new ClientResource(HitchhikingComponent.BASE_URL + "/hitchhiking?answer=41");
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
        ClientResource clientResource = new ClientResource(HitchhikingComponent.BASE_URL + "/hitchhiking?answer=42");
        String text = clientResource.get().getText();
        assertEquals("thumbs up", text);
    }
}
