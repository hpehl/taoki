package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.util.Series;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class AppEngineSecurityCheck implements SecurityCheck
{
    public static final String TOKEN_ATTRIBUTE = "token";
    public static final String APPENGINE_COOKIE = "ACSID";


    @Override
    public void check(Request request, Response response) throws SecurityException
    {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null)
        {
            throw new SecurityException("No user");
        }

        String token = (String) request.getAttributes().get(TOKEN_ATTRIBUTE);
        if (token == null || token.length() == 0)
        {
            throw new SecurityException("No security token");
        }

        String sessionId = findSessionId(request);
        String serverName = request.getResourceRef().getHostDomain();
        if (!("localhost".equals(serverName)) && !(token.equals(sessionId)))
        {
            throw new SecurityException("Invalid security token");
        }
    }


    private String findSessionId(Request request)
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
