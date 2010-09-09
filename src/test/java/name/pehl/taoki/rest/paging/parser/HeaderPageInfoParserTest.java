package name.pehl.taoki.rest.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;

import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision: 94
 *          $
 */
public class HeaderPageInfoParserTest extends AbstractPageInfoParserTest
{
    @Before
    public void setUp() throws Exception
    {
        underTest = new HeaderPageInfoParser();
    }


    @Test
    public void parseInvalid()
    {
        try
        {
            underTest.parse(FOO);
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }

        try
        {
            underTest.parse("items=0-");
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }

        try
        {
            underTest.parse("items=0-x");
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }
        try
        {
            underTest.parse("items=0-9;");
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }

        try
        {
            underTest.parse("items=0-9;foo:");
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }

        try
        {
            underTest.parse("items=0-9;foo:bar");
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }
    }


    @Test
    public void parseValid() throws PageInfoParseException
    {
        PageInfo pageInfo = null;

        pageInfo = underTest.parse("items=0-9");
        assertPageInfo(pageInfo, 0, 10);

        pageInfo = underTest.parse("items=0-9;foo");
        assertPageInfo(pageInfo, 0, 10, FOO);

        pageInfo = underTest.parse("items=0-9;foo:asc");
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.ASC);

        pageInfo = underTest.parse("items=0-9;foo:desc");
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.DESC);

        pageInfo = underTest.parse("items=0-9;foo:none");
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.NONE);
    }
}
