package name.pehl.gwt.taoki.client.rest;

import java.io.IOException;

import name.pehl.gwt.piriti.client.xml.XmlReader;

import org.restlet.gwt.Request;
import org.restlet.gwt.Response;

/**
 * @param <M>
 *            Die Modelklasse
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-06-12 16:27:19 +0200 (Fr, 12 Jun 2009) $ $Revision:
 *          69904 $
 */
public abstract class ModelCallback<T> extends XmlCallback
{
    protected final XmlReader<T> xmlReader;


    public ModelCallback(XmlReader<T> xmlReader)
    {
        this.xmlReader = xmlReader;
    }


    @Override
    public void onSuccess(Request request, Response response)
    {
        try
        {
            T model = xmlReader.readSingle(getDocument(response));
            onSuccess(request, response, model);
        }
        catch (IOException e)
        {
            onError(request, response);
        }
    }


    public abstract void onSuccess(Request request, Response response, T model);
}
