package cn.redsoft.magician.core.auth.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
@JsonSerialize(using = DefaultOauth2ExceptionSerializer.class)
public class DefaultOauth2Exception extends OAuth2Exception {

    public DefaultOauth2Exception(String message) {
        super(message);
    }
}
