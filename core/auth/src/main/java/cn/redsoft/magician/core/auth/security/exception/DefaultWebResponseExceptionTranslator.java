package cn.redsoft.magician.core.auth.security.exception;

import cn.redsoft.magician.core.common.utils.Rs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component("defaultWebResponseExceptionTranslator")
public class DefaultWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultWebResponseExceptionTranslator.class);

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        LOGGER.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new DefaultOauth2Exception(e.getMessage()));

    }
}
