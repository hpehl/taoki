package name.pehl.taoki;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.io.BioUtils;
import org.restlet.representation.WriterRepresentation;
import org.restlet.resource.ResourceException;

/**
 * Representation for large amount of data. Internally the data is buffered in a
 * temporary file. Using {@link #getWriter()} you can access the writer of this
 * file.
 * <p>
 * If not specified {@link MediaType#TEXT_XML} is used as the representation for
 * this resource.
 * <p>
 * After the data is transfered to the client the temporary file is deleted.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 93
 *          $
 */
public class FatRepresentation extends WriterRepresentation
{
    private File file;
    private FileWriter writer;


    /**
     * Creates a new instance of this resource with {@link MediaType#TEXT_XML}.
     * Creates a new temporary file and initializes the writer for it.
     */
    public FatRepresentation()
    {
        this(MediaType.TEXT_XML);
    }


    /**
     * Creates a new instance of this resource with the specified media type.
     * Creates a new temporary file and initializes the writer for it.
     * 
     * @param mediaType
     *            the media type
     */
    public FatRepresentation(MediaType mediaType)
    {
        super(mediaType);
        try
        {
            // *F*at *R*epresentation
            this.file = File.createTempFile("fr_", null);
            this.writer = new FileWriter(file);
        }
        catch (IOException e)
        {
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "Cannot create temporary file", e);
        }
    }


    /**
     * Returns the writer to the temporary file.
     * 
     * @return the writer to the temporary file.
     */
    public FileWriter getWriter()
    {
        return writer;
    }


    /**
     * Writes the data to the temporary file. This method is called from the
     * restlet framework.
     * 
     * @param writer
     *            The characters writer.
     * @throws IOException
     * @see org.restlet.representation.Representation#write(java.io.Writer)
     */
    @Override
    public void write(Writer writer) throws IOException
    {
        this.writer.flush();
        this.writer.close();
        setSize(this.file.length());

        FileReader reader = new FileReader(file);
        BioUtils.copy(reader, writer);
    }


    /**
     * Deletes the temporary file.
     * 
     * @see org.restlet.representation.WriterRepresentation#release()
     */
    @Override
    public void release()
    {
        super.release();
        file.delete();
    }
}
