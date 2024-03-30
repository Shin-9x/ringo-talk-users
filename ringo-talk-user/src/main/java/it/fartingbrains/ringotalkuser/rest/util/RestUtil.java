package it.fartingbrains.ringotalkuser.rest.util;

import it.fartingbrains.ringotalkuser.rest.exception.UnauthorizedUserException;
import it.fartingbrains.ringotalkuser.rest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUtil {
    private static final Logger _logger = LoggerFactory.getLogger(RestUtil.class);

    public static void authenticate(String token, String username, UserService userService) {
        String errMessage;
        if(token == null || token.isEmpty()) {
            errMessage = "[authenticate] Token is empty or null.";
            if(_logger.isErrorEnabled()) { _logger.error(errMessage); }
            throw new IllegalArgumentException(errMessage);
        }
        if(!userService.validateToken(token, username)) {
            errMessage = String.format("[authenticate] Token [%s] not valid.", token);
            if(_logger.isErrorEnabled()) { _logger.error(errMessage); }
            throw new UnauthorizedUserException(errMessage);
        }
    }
}
