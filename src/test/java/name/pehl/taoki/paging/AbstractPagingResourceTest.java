package name.pehl.taoki.paging;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;
import name.pehl.taoki.paging.parser.PageInfoParseException;
import name.pehl.taoki.paging.parser.PageInfoParser;

import org.junit.Test;
import org.restlet.Request;

/**
 * @author $Author$
 * @version $Date$ $Revision: 209
 *          $
 */
public class AbstractPagingResourceTest
{
    @Test
    public void getPageInfoWithNullParser()
    {
        AbstractPagingResource underTest = new NullPagingResource();
        PageInfo pageInfo = underTest.getPageInfo();
        assertNull(pageInfo);
    }


    @Test
    public void getPageInfoWithNullInput() throws PageInfoParseException
    {
        PageInfoParser pip = mock(PageInfoParser.class);
        when(pip.parse(null)).thenReturn(null);

        AbstractPagingResource underTest = new NullPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertNull(pageInfo);
        verify(pip).parse(null);
    }


    @Test
    public void getPageInfoWithInvalidInput() throws PageInfoParseException
    {
        PageInfoParser pip = mock(PageInfoParser.class);
        when(pip.parse(FooPagingResource.FOO)).thenThrow(
                new PageInfoParseException("Expected exception from junit test method"));

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertNull(pageInfo);
    }


    @Test
    public void getPageInfo() throws PageInfoParseException
    {
        PageInfo pageInfoFixture = new PageInfo(1, 2);
        PageInfoParser pip = mock(PageInfoParser.class);
        when(pip.parse(FooPagingResource.FOO)).thenReturn(pageInfoFixture);

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertEquals(pageInfoFixture, pageInfo);
        verify(pip).parse(FooPagingResource.FOO);
    }

    // ---------------------------------------------------------- inner classes

    static class NullPagingResource extends AbstractPagingResource
    {
        public NullPagingResource()
        {
            this(null);
        }


        public NullPagingResource(PageInfoParser pip)
        {
            super(pip);
        }


        @Override
        protected Object getInput(Request request)
        {
            return null;
        }
    }

    static class FooPagingResource extends AbstractPagingResource
    {
        static final String FOO = "foo";


        public FooPagingResource(PageInfoParser pip)
        {
            super(pip);
        }


        @Override
        protected Object getInput(Request request)
        {
            return FOO;
        }
    }
}
