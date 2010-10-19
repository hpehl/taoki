package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ServerResource;

/**
 * Interface for a security check of a {@link ServerResource}.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface SecurityCheck
{
    /**
     * Checks whether the resource is available. All information must be
     * provided in the {@link Request} and {@link Response} objects.
     * 
     * @param request
     * @param response
     * @throws SecurityException
     *             if the check failed.
     */
    void check(Request request, Response response) throws SecurityException;
}
