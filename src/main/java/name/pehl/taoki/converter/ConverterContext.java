package name.pehl.taoki.converter;

import java.util.HashMap;
import java.util.Map;


/**
 * @author $Author$
 * @version $Revision$
 */
public class ConverterContext
{
    private Map<String, Object> data;


    public ConverterContext()
    {
        data = new HashMap<String, Object>();
    }


    public Object get(String key)
    {
        return data.get(key);
    }


    public void set(String key, Object value)
    {
        data.put(key, value);
    }


    public Map<String, Object> asMap()
    {
        return data;
    }
}
