package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;

/**
 * Interface for reading the security token.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public interface SecurityTokenReader
{
    /**
     * Reads the security token from the request and / or response.
     * 
     * @param request
     * @param response
     * @return the security token
     * @throws SecurityException
     *             if there's no token.
     */
    String readToken(Request request, Response response) throws SecurityException;
}
