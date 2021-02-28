package com.hamzatugrul.surl.validator;

import com.google.common.base.Strings;
import com.hamzatugrul.surl.exception.InvalidURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Objects;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */
public class SUrlValidator {
    private static final Logger logger = LoggerFactory.getLogger(SUrlValidator.class);

    public static URI checkUrlValidityAndReturnUri(String url) throws InvalidURLException {
        try {
            Objects.requireNonNull(url);
            URI uri = URI.create(url);

            //This is also checking the validity of url
            logger.info("URL is in validation:" + uri.toURL());

            return uri;
        }
        catch (Exception ex) {
            logger.error("Error happened while checking URL validity. Ex=" + ex.getMessage());
            throw new InvalidURLException("Valid URL should be given!");
        }
    }

    public static void checkShortURlKey(String shortUrl) throws InvalidURLException {
        if (Strings.isNullOrEmpty(shortUrl)) {
            throw new InvalidURLException("Requested URL Key is not valid!");
        }
    }
}
