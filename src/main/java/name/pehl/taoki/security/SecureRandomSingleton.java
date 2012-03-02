package name.pehl.taoki.security;

import java.security.SecureRandom;

import com.google.inject.Singleton;

/**
 * Singelton {@link SecureRandom}.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@Singleton
public class SecureRandomSingleton extends SecureRandom
{
    private static final long serialVersionUID = 462441711297897572L;
}
