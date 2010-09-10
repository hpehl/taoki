package name.pehl.taoki.paging;

import static org.junit.Assert.*;

import name.pehl.taoki.paging.SortDir;
import name.pehl.taoki.paging.SortInfo;

import org.junit.Test;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-13 11:17:38 +0100 (Di, 13 Jan 2009) $ $Revision:
 *          61052 $
 */
public class SortInfoTest
{
    @Test
    public void testShouldSort()
    {
        SortInfo underTest = null;

        underTest = new SortInfo();
        assertFalse(underTest.shouldSort());

        underTest = new SortInfo("");
        assertFalse(underTest.shouldSort());
        underTest = new SortInfo("field");
        assertFalse(underTest.shouldSort());

        underTest = new SortInfo(null, SortDir.NONE);
        assertFalse(underTest.shouldSort());
        underTest = new SortInfo("", SortDir.NONE);
        assertFalse(underTest.shouldSort());
        underTest = new SortInfo("field", SortDir.NONE);
        assertFalse(underTest.shouldSort());

        underTest = new SortInfo(null, SortDir.ASC);
        assertFalse(underTest.shouldSort());
        underTest = new SortInfo("", SortDir.ASC);
        assertTrue(underTest.shouldSort());
        underTest = new SortInfo("field", SortDir.ASC);
        assertTrue(underTest.shouldSort());

        underTest = new SortInfo(null, SortDir.DESC);
        assertFalse(underTest.shouldSort());
        underTest = new SortInfo("", SortDir.DESC);
        assertTrue(underTest.shouldSort());
        underTest = new SortInfo("field", SortDir.DESC);
        assertTrue(underTest.shouldSort());
    }
}
