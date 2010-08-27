package name.pehl.taoki.rest.paging;

import static junit.framework.Assert.*;
import static org.easymock.EasyMock.*;
import name.pehl.taoki.rest.paging.parser.PageInfoParseException;
import name.pehl.taoki.rest.paging.parser.PageInfoParser;

import org.junit.Test;
import org.restlet.Request;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class AbstractPagingResourceTest
{
    @Test
    public void getPageInfoWithNullParser()
    {
        Request request = createStrictMock(Request.class);
        replay(request);

        AbstractPagingResource underTest = new NullPagingResource();
        PageInfo pageInfo = underTest.getPageInfo(request);
        assertNull(pageInfo);
        verify(request);
    }


    @Test
    public void getPageInfoWithNullInput() throws PageInfoParseException
    {
        Request request = createStrictMock(Request.class);
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(null)).andReturn(null);
        replay(request, pip);

        AbstractPagingResource underTest = new NullPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo(request);
        assertNull(pageInfo);
        verify(request, pip);
    }


    @Test
    public void getPageInfoWithInvalidInput() throws PageInfoParseException
    {
        Request request = createStrictMock(Request.class);
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(FooPagingResource.FOO)).andThrow(
                new PageInfoParseException("Expected exception from junit test method"));
        replay(request, pip);

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo(request);
        assertNull(pageInfo);
        verify(request, pip);
    }


    @Test
    public void getPageInfo() throws PageInfoParseException
    {
        PageInfo pageInfoFixture = new PageInfo(1, 2);
        Request request = createStrictMock(Request.class);
        PageInfoParser pip = createStrictMock(PageInfoParser.class);
        expect(pip.parse(FooPagingResource.FOO)).andReturn(pageInfoFixture);
        replay(request, pip);

        AbstractPagingResource underTest = new FooPagingResource(pip);
        PageInfo pageInfo = underTest.getPageInfo(request);
        assertEquals(pageInfoFixture, pageInfo);
        verify(request, pip);
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
