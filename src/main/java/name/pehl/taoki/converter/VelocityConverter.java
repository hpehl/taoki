package name.pehl.taoki.converter;

import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Revision$
 */
public class VelocityConverter<T> implements Converter<T>
{
    public static final String TEMPLATE_PATH = "name.pehl.taoki.converter.velocityTemplatePath";

    private Class<T> modelType;
    private final String modelVariable;
    private final String modelsVariable;
    private final Logger logger;
    private final VelocityEngine velocityEngine;


    @Inject
    @SuppressWarnings("unchecked")
    public VelocityConverter(VelocityEngine velocityEngine, Logger logger)
    {
        // the current class
        Class<?> clazz = getClass();

        // the class ot the type argument
        this.modelType = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        String simpleName = modelType.getSimpleName();
        StringBuilder variable = new StringBuilder();
        variable.append(simpleName.substring(0, 1).toLowerCase());
        variable.append(simpleName.substring(1));
        this.modelVariable = variable.toString();
        variable.append("Collection");
        this.modelsVariable = variable.toString();

        this.velocityEngine = velocityEngine;
        this.logger = logger;
    }


    @Override
    public Representation convert(T model, ConverterContext context) throws ConverterException
    {
        String template = extractTemplate(context);
        VelocityContext velocityContext = createVelocityContext(context);
        if (context == null)
        {
            velocityContext.put(modelVariable, model);
        }
        String xml = internalConvert(template, velocityContext);
        Representation representation = new StringRepresentation(xml, MediaType.TEXT_XML);
        representation.setCharacterSet(CharacterSet.UTF_8);
        return representation;
    }


    @Override
    public Representation convert(Collection<T> models, ConverterContext context) throws ConverterException
    {
        String template = extractTemplate(context);
        VelocityContext velocityContext = createVelocityContext(context);
        if (context == null)
        {
            velocityContext.put(modelsVariable, models);
        }
        String xml = internalConvert(template, velocityContext);
        Representation representation = new StringRepresentation(xml, MediaType.TEXT_XML);
        representation.setCharacterSet(CharacterSet.UTF_8);
        return representation;
    }


    private String extractTemplate(ConverterContext context)
    {
        return null;
    }


    private VelocityContext createVelocityContext(ConverterContext context)
    {
        VelocityContext velocityContext;
        if (context == null)
        {
            velocityContext = new VelocityContext();
        }
        else
        {
            velocityContext = new VelocityContext(context.asMap());
        }
        return velocityContext;
    }


    private String internalConvert(String template, VelocityContext velocityContext)
    {
        try
        {
            StringWriter writer = new StringWriter();
            velocityEngine.mergeTemplate(template, "UTF-8", velocityContext, writer);
            return writer.toString();
        }
        catch (ResourceNotFoundException e)
        {
            String errorMessage = "Cannot find template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (ParseErrorException e)
        {
            String errorMessage = "Cannot parse template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (MethodInvocationException e)
        {
            String errorMessage = "Error when executing methods in template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (Exception e)
        {
            String errorMessage = "Unexpected error in template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
    }
}
