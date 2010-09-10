package name.pehl.taoki.security;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class SecurityException extends ResourceException
{
    public SecurityException(String reason)
    {
        super(Status.CLIENT_ERROR_FORBIDDEN, reason);
    }
}
