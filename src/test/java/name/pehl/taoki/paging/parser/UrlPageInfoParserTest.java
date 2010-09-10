package name.pehl.taoki.paging.parser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.SortDir;
import name.pehl.taoki.paging.parser.PageInfoParseException;
import name.pehl.taoki.paging.parser.PageInfoParser;
import name.pehl.taoki.paging.parser.UrlPageInfoParser;

import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class UrlPageInfoParserTest extends AbstractPageInfoParserTest
{
    @Before
    public void setUp() throws Exception
    {
        underTest = new UrlPageInfoParser();
    }


    @Test
    public void parseInvalid()
    {
        try
        {
            underTest.parse(attributesFor(0, 10, FOO, "meep"));
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

        pageInfo = underTest.parse(attributesFor(0, 10));
        assertPageInfo(pageInfo, 0, 10);

        pageInfo = underTest.parse(attributesFor(0, 10, FOO));
        assertPageInfo(pageInfo, 0, 10, FOO);

        pageInfo = underTest.parse(attributesFor(0, 10, FOO, SortDir.ASC.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.ASC);

        pageInfo = underTest.parse(attributesFor(0, 10, FOO, SortDir.DESC.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.DESC);

        pageInfo = underTest.parse(attributesFor(0, 10, FOO, SortDir.NONE.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.NONE);
    }


    private Map<String, Object> attributesFor(int offset, int pageSize)
    {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(PageInfoParser.OFFSET, String.valueOf(offset));
        attributes.put(PageInfoParser.PAGE_SIZE, String.valueOf(pageSize));
        return attributes;
    }


    private Map<String, Object> attributesFor(int offset, int pageSize, String sortField)
    {
        Map<String, Object> attributes = attributesFor(offset, pageSize);
        attributes.put(PageInfoParser.SORT_FIELD, sortField);
        return attributes;
    }


    private Map<String, Object> attributesFor(int offset, int pageSize, String sortField, String sortDir)
    {
        Map<String, Object> attributes = attributesFor(offset, pageSize, sortField);
        attributes.put(PageInfoParser.SORT_DIR, sortDir);
        return attributes;
    }
}
