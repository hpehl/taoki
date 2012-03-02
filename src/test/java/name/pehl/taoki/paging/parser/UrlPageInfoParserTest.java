package name.pehl.taoki.paging.parser;

import java.util.HashMap;
import java.util.Map;

import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.PageInfoParseException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision: 145
 *          $
 */
public class UrlPageInfoParserTest extends AbstractPageInfoParserTest
{
    @Before
    public void setUp() throws Exception
    {
        underTest = new UrlPageInfoParser();
    }


    @Test
    public void parseValid() throws PageInfoParseException
    {
        PageInfo pageInfo = null;

        pageInfo = underTest.parse(attributesFor(0, 10));
        assertPageInfo(pageInfo, 0, 10);
    }


    private Map<String, Object> attributesFor(int offset, int pageSize)
    {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(PageInfoParser.OFFSET, String.valueOf(offset));
        attributes.put(PageInfoParser.PAGE_SIZE, String.valueOf(pageSize));
        return attributes;
    }
}
