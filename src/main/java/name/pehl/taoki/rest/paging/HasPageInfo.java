package name.pehl.taoki.rest.paging;

import org.restlet.Request;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public interface HasPageInfo
{
    PageInfo getPageInfo(Request request);
}
