package name.pehl.taoki.security;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HitchhikingResourceSecuredAnnotation extends ServerResource
{
    @Secured
    @Override
    protected Representation get() throws ResourceException
    {
        return new StringRepresentation("thumbs up", MediaType.TEXT_PLAIN);
    }
}
