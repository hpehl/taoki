package name.pehl.gwt.taoki.client.rest;

import java.io.IOException;
import java.util.List;

import name.pehl.gwt.piriti.client.xml.XmlReader;

import org.restlet.gwt.Request;
import org.restlet.gwt.Response;

/**
 * @param <M>
 *            Die Modelklasse
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-05-25 16:09:58 +0200 (Mo, 25 Mai 2009) $ $Revision:
 *          69339 $
 */
public abstract class ModelsCallback<T> extends XmlCallback
{
    protected final XmlReader<T> xmlReader;
    protected final String xpath;


    public ModelsCallback(XmlReader<T> xmlReader, String xpath)
    {
        this.xmlReader = xmlReader;
        this.xpath = xpath;
    }


    @Override
    public void onSuccess(Request request, Response response)
    {
        try
        {
            List<T> models = xmlReader.readList(getDocument(response), xpath);
            onSuccess(request, response, models);
        }
        catch (IOException e)
        {
            onError(request, response);
        }
    }


    public abstract void onSuccess(Request request, Response response, List<T> models);
}
