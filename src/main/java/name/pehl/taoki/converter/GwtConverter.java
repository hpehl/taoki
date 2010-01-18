package name.pehl.taoki.converter;

import java.util.Collection;

import org.restlet.representation.Representation;



/**
 * @author $Author:$ 
 * @version $Date:$ $Revision:$
 */
public class GwtConverter<T> implements Converter<T>
{
    @Override
    public Representation convert(T model, ConverterContext context) throws ConverterException
    {
        // TODO Implement me!
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Representation convert(Collection<T> models, ConverterContext context) throws ConverterException
    {
        // TODO Implement me!
        throw new UnsupportedOperationException("Not implemented");
    }

}
