package name.pehl.gwt.taoki.client.rest;

import org.restlet.gwt.Request;
import org.restlet.gwt.Response;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-09 10:29:22 +0100 (Fr, 09 Jan 2009) $ $Revision:
 *          60899 $
 */
public class VoidCallback extends RestCallback
{
    @Override
    public void onSuccess(Request request, Response response)
    {
        // nop
    }
}
