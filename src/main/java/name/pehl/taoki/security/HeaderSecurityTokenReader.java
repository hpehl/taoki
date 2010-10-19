package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Parameter;
import org.restlet.engine.http.header.HeaderConstants;
import org.restlet.util.Series;

/**
 * Reads the security token from a request header.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class HeaderSecurityTokenReader implements SecurityTokenReader
{
    private final String name;


    public HeaderSecurityTokenReader(@SecurityToken final String name)
    {
        this.name = name;
    }


    /**
     * @param request
     * @param response
     * @return
     * @see name.pehl.taoki.security.SecurityTokenReader#readToken(org.restlet.Request,
     *      org.restlet.Response)
     */
    @Override
    @SuppressWarnings("unchecked")
    public String readToken(Request request, Response response)
    {
        String token = null;
        Series<Parameter> header = (Series<Parameter>) request.getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (header != null)
        {
            token = header.getFirstValue(name);
        }
        if (token == null)
        {
            throw new SecurityException("No security token found for " + request.getResourceRef().toUrl());
        }
        return token;
    }
}
