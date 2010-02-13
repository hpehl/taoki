package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface SecurityCheck
{
    void check(Request request, Response response) throws SecurityException;
}
