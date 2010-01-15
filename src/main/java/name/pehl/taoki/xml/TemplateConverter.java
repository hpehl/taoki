package name.pehl.taoki.xml;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public interface TemplateConverter
{
    String convert(String template, Context context) throws ConverterException;
}
