package name.pehl.taoki.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.paging.PageInfo;

import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision$
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
    }
}
