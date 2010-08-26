package name.pehl.taoki.rest;

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
 * Representation die für umfangreiche Daten verwendet werden sollte. Intern
 * werden die Daten in einer temporäre Datei gepuffert, deren Inhalt dann zum
 * Client übertragen wird. Per {@link #getWriter()} kann der {@link Writer} für
 * die temporäre Datei abgerufen werden.
 * <p>
 * Falls nicht anders angegeben arbeitet die Representation mit dem Mediatyp
 * {@link MediaType#TEXT_XML}.
 * <p>
 * Nachdem die Daten zum Client übertragen wurden, wird die temporäre Datei
 * wieder gelöscht.
 * 
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class FatRepresentation extends WriterRepresentation
{
    private File file;
    private FileWriter writer;


    /**
     * Erzeugt die Representation mit dem Mediatyp {@link MediaType#TEXT_XML}.
     * Erzeugt die temporäre Datei und initialisiert den {@link Writer} darauf.
     */
    public FatRepresentation()
    {
        this(MediaType.TEXT_XML);
    }


    /**
     * Erzeugt die Representation mit dem angegebenen Mediatyp. Erzeugt die
     * temporäre Datei und initialisiert den {@link Writer} darauf.
     * 
     * @param mediaType
     */
    public FatRepresentation(MediaType mediaType)
    {
        super(mediaType);
        try
        {
            // *E*soves *F*at *R*epresentation
            this.file = File.createTempFile("efr_", null);
            this.writer = new FileWriter(file);
        }
        catch (IOException e)
        {
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL,
                    "Kann temporäre Datei für FatRepresentation nicht erzeugen", e);
        }
    }


    /**
     * Liefert den {@link Writer} für die temporäre Datei.
     * 
     * @return
     */
    public FileWriter getWriter()
    {
        return writer;
    }


    /**
     * Schreibt die Daten der temporären Datei in den angegebenen Writer. Diese
     * Methode wird vom Restlet Framework aufgerufen, wenn der Client die Daten
     * anfordert.
     * 
     * @param writer
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
     * Löscht die temporäre Datei.
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
