package name.pehl.taoki.rest.paging.parser;

import static org.junit.Assert.*;
import name.pehl.taoki.rest.paging.PageInfo;
import name.pehl.taoki.rest.paging.SortDir;

import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Form;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class QueryPageInfoParserTest extends AbstractPageInfoParserTest
{
    @Before
    public void setUp() throws Exception
    {
        underTest = new QueryPageInfoParser();
    }


    @Test
    public void parseInvalid()
    {
        try
        {
            underTest.parse(new Object());
            fail(PAGE_INFO_PARSE_EXCEPTION_EXPECTED);
        }
        catch (PageInfoParseException e)
        {
            // nop
        }

        try
        {
            underTest.parse(formFor(0, 10, FOO, "meep"));
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

        pageInfo = underTest.parse(formFor(0, 10));
        assertPageInfo(pageInfo, 0, 10);

        pageInfo = underTest.parse(formFor(0, 10, FOO));
        assertPageInfo(pageInfo, 0, 10, FOO);

        pageInfo = underTest.parse(formFor(0, 10, FOO, SortDir.ASC.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.ASC);

        pageInfo = underTest.parse(formFor(0, 10, FOO, SortDir.DESC.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.DESC);

        pageInfo = underTest.parse(formFor(0, 10, FOO, SortDir.NONE.name()));
        assertPageInfo(pageInfo, 0, 10, FOO, SortDir.NONE);
    }


    private Form formFor(int offset, int limit)
    {
        Form form = new Form();
        form.add(PageInfoParser.OFFSET, String.valueOf(offset));
        form.add(PageInfoParser.LIMIT, String.valueOf(limit));
        return form;
    }


    private Form formFor(int offset, int limit, String sortField)
    {
        Form form = formFor(offset, limit);
        form.add(PageInfoParser.SORT_FIELD, sortField);
        return form;
    }


    private Form formFor(int offset, int limit, String sortField, String sortDir)
    {
        Form form = formFor(offset, limit, sortField);
        form.add(PageInfoParser.SORT_DIR, sortDir);
        return form;
    }
}
