package name.pehl.gwt.taoki.client.rest;

import org.restlet.gwt.Request;
import org.restlet.gwt.Response;
import org.restlet.gwt.Uniform;
import org.restlet.gwt.data.Status;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-06-30 12:03:49 +0200 (Di, 30 Jun 2009) $ $Revision:
 *          70362 $
 */
public abstract class RestCallback implements Uniform
{
    @Override
    public void handle(Request request, Response response)
    {
        if (response.getStatus().isError())
        {
            onError(request, response);
        }
        else
        {
            onSuccess(request, response);
        }
    }


    public abstract void onSuccess(Request request, Response response);


    /**
     * Liest den Status aus der Response und erzeugt damit einen
     * {@linkplain AppEvent} vom Typ
     * {@linkplain TireApplication.Application#ERROR} .
     * 
     * @param request
     * @param response
     */
    public void onError(Request request, Response response)
    {
        @SuppressWarnings("unused") Status status = response.getStatus();
        // TODO Central error handling
    }
}
