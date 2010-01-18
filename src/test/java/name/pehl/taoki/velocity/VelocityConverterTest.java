package name.pehl.taoki.velocity;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import name.pehl.taoki.converter.ConverterException;
import name.pehl.taoki.converter.VelocityConverter;
import name.pehl.taoki.converter.VelocityProperties;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class VelocityConverterTest
{
    public static final String TEMPLATE = "templates/hello.vm";
    
    private VelocityConverter underTest;


    @Before
    public void setUp() throws Exception
    {
        VelocityProperties properties = new VelocityProperties();
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        Logger logger = Logger.getLogger(getClass().getName());
        underTest = new VelocityConverter(velocityEngine, logger);
    }


    @Ignore
    @Test(expected = ConverterException.class)
    public void testInvalidTemplate()
    {
        underTest.convert(null, null);
        fail("Not yet implemented");
    }
    
    @Test
    @Ignore
    public void testConvertWithoutContext()
    {
//        String xml = underTest.convert(TEMPLATE, null);
//        assertNotNull(xml);
//        assertTrue(xml.contains("$name"));
    }

    @Test
    @Ignore
    public void testConvertWithContext()
    {
//        Context context = new Context();
//        context.set("name", "Harald");
//        String xml = underTest.convert(TEMPLATE, context);
//        assertNotNull(xml);
//        assertTrue(xml.contains("Harald"));
    }
}
