package name.pehl.taoki.converter;

import org.restlet.data.MediaType;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public interface ConverterFactory
{
    <T> Converter<T> createConverter(MediaType mediaType);
}
