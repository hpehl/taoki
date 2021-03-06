package name.pehl.taoki.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Servlet filter which uses a {@link SecurityTokenGenerator} to add a cookie to
 * the {@link HttpServletResponse}.
 * <p>
 * The name of the cookie is the parameter in
 * {@link #SecurityCookieFilter(String, SecurityTokenGenerator)}, the value is
 * the security token generated by
 * {@link SecurityTokenGenerator#generateToken()}.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 175
 *          $
 */
@Singleton
public class SecurityCookieFilter implements Filter
{
    private final String name;
    private final String token;


    @Inject
    public SecurityCookieFilter(@SecurityToken final String name, final SecurityTokenGenerator securityTokenGenerator)
    {
        this.name = name;
        this.token = securityTokenGenerator.generateToken();
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }


    @Override
    public void destroy()
    {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException
    {
        if (request instanceof HttpServletRequest)
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Cookie securityCookie = new Cookie(name, token);
            securityCookie.setMaxAge(-1);
            securityCookie.setPath("/");
            httpResponse.addCookie(securityCookie);
        }
        chain.doFilter(request, response);
    }
}
