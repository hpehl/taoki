package name.pehl.taoki.rest.paging;

import static junit.framework.Assert.*;
import static org.easymock.EasyMock.*;
import name.pehl.taoki.rest.paging.parser.PageInfoParseException;
import name.pehl.taoki.rest.paging.parser.PageInfoParser;

import org.junit.Test;
import org.restlet.Request;

/**
 * @author $Author$
 * @version $Date$ $Revision: 99
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
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(null)).andReturn(null);
        replay(pip);

        AbstractPagingResource underTest = new NullPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertNull(pageInfo);
        verify(pip);
    }


    @Test
    public void getPageInfoWithInvalidInput() throws PageInfoParseException
    {
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(FooPagingResource.FOO)).andThrow(
                new PageInfoParseException("Expected exception from junit test method"));
        replay(pip);

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertNull(pageInfo);
        verify(pip);
    }


    @Test
    public void getPageInfo() throws PageInfoParseException
    {
        PageInfo pageInfoFixture = new PageInfo(1, 2);
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(FooPagingResource.FOO)).andReturn(pageInfoFixture);
        replay(pip);

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo();
        assertEquals(pageInfoFixture, pageInfo);
        verify(pip);
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
