package name.pehl.taoki.security;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HitchhikingResourceGetAnnotation extends ServerResource
{
    @Get("text")
    public String represent()
    {
        return "thumbs up";
    }
}
