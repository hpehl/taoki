package name.pehl.taoki.converter;

import java.util.Collection;

import org.restlet.representation.Representation;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public interface Converter<T>
{
    Representation convert(T model, ConverterContext context) throws ConverterException;


    Representation convert(Collection<T> models, ConverterContext context) throws ConverterException;
}
