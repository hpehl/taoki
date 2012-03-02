package name.pehl.taoki.security;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

/**
 * Exception thrown by {@link SecurityCheck}.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 149
 *          $
 */
public class SecurityException extends ResourceException
{
    /**
     * Creates a new resource exception with
     * {@link Status#CLIENT_ERROR_FORBIDDEN} and the specified reason.
     * 
     * @param reason
     */
    public SecurityException(String reason)
    {
        super(Status.CLIENT_ERROR_FORBIDDEN, reason);
    }
}
