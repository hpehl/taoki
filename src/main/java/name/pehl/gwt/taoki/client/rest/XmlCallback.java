package name.pehl.gwt.taoki.client.rest;

import java.io.IOException;

import org.restlet.gwt.Response;
import org.restlet.gwt.ext.xml.DomRepresentation;

import com.google.gwt.xml.client.Document;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public abstract class XmlCallback extends RestCallback
{
    protected Document getDocument(Response response) throws IOException
    {
        DomRepresentation representation = new DomRepresentation(response.getEntity());
        return representation.getDocument();
    }
}
