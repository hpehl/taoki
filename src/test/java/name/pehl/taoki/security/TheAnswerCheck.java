package name.pehl.taoki.security;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class TheAnswerCheck implements SecurityCheck
{
    @Override
    public void check(Request request, Response response) throws SecurityException
    {
        Form form = request.getResourceRef().getQueryAsForm();
        String answer = form.getFirstValue("answer");
        if (answer == null)
        {
            throw new SecurityException("No answer");
        }
        if (!"42".equals(answer))
        {
            throw new SecurityException("Wrong answer");
        }
    }
}
