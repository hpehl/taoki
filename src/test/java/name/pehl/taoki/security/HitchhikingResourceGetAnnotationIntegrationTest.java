package name.pehl.taoki.security;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HitchhikingResourceGetAnnotationIntegrationTest extends HitchhikingIntegrationTest
{
    @Override
    protected AbstractModule getModule()
    {
        return new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(SecurityCheck.class).to(TheAnswerCheck.class);
                bind(HitchhikingResourceGetAnnotation.class);
            }
        };
    }


    @Override
    protected Class<? extends ServerResource> getResourceClass()
    {
        return HitchhikingResourceGetAnnotation.class;
    }
}
