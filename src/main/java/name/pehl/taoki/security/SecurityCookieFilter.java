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
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@Singleton
public class SecurityCookieFilter implements Filter
{
    private final String name;
    private final SecurityTokenGenerator securityTokenGenerator;


    @Inject
    public SecurityCookieFilter(@SecurityToken final String name, final SecurityTokenGenerator securityTokenGenerator)
    {
        this.name = name;
        this.securityTokenGenerator = securityTokenGenerator;
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
            Cookie securityCookie = new Cookie(name, securityTokenGenerator.generateToken());
            securityCookie.setMaxAge(-1);
            securityCookie.setPath("/");
            httpResponse.addCookie(securityCookie);
        }
        chain.doFilter(request, response);
    }
}
