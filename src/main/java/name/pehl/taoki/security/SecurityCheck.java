package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.data.Cookie;
import org.restlet.util.Series;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class SecurityCheck
{
    public static final String TOKEN_ATTRIBUTE = "token";
    public static final String APPENGINE_COOKIE = "ACSID";

    private final Request request;


    public SecurityCheck(Request request)
    {
        this.request = request;
    }


    public void check()
    {
        String token = (String) request.getAttributes().get(TOKEN_ATTRIBUTE);
        if (token == null || token.length() == 0)
        {
            throw new SecurityException("No security token");
        }

        String sessionId = findSessionId();
        String serverName = request.getResourceRef().getHostDomain();
        if (!("localhost".equals(serverName)) && !(token.equals(sessionId)))
        {
            throw new SecurityException("Security token invalid");
        }
    }


    private String findSessionId()
    {
        String result = null;
        Series<Cookie> cookies = request.getCookies();
        for (Cookie cookie : cookies)
        {
            if (APPENGINE_COOKIE.equals(cookie.getName()))
            {
                result = cookie.getValue();
                break;
            }
        }
        return result;
    }
}
