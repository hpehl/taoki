package name.pehl.taoki.xml;

import name.pehl.taoki.converter.ConverterException;

/**
 * @author $Author$
 * @version $Revision$
 */
public interface TemplateConverter
{
    String convert(String template, Context context) throws ConverterException;
}
