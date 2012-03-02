package name.pehl.taoki.security;

import java.math.BigInteger;

import com.google.inject.Inject;

/**
 * A security token generator which used {@link SecureRandomSingleton} to
 * generate the token.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 175
 *          $
 */
public class RandomSecurityTokenGenerator implements SecurityTokenGenerator
{
    private final SecureRandomSingleton srs;


    @Inject
    public RandomSecurityTokenGenerator(final SecureRandomSingleton srs)
    {
        this.srs = srs;
    }


    @Override
    public String generateToken()
    {
        return new BigInteger(130, srs).toString(32);
    }
}
