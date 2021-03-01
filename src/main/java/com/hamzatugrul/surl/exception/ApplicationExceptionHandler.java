package com.hamzatugrul.surl.exception;

import com.hamzatugrul.surl.model.BaseErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URISyntaxException;

import static com.hamzatugrul.surl.enums.EnmUrlShortenerResults.*;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseErrorResponse> handleInvalidUrlException(InvalidURLException exception) {
        logger.error("URL Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorResponse(INVALID_URL.getCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseErrorResponse> handleInvalidUrlException(KeyNotFoundException exception) {
        logger.error("Key Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorResponse(INVALID_URL.getCode(), INVALID_URL.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseErrorResponse> handleInvalidUrlException(URISyntaxException exception) {
        logger.error("Request Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorResponse(INVALID_LONG_URL.getCode(), INVALID_LONG_URL.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handle(MethodArgumentNotValidException exception) {
        logger.error("Method Validation Exception:{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseErrorResponse(INVALID_PARAMS.getCode(), exception.getBindingResult().getFieldErrors().toString()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handle(HttpMessageNotReadableException exception) {
        logger.error("Request Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorResponse(INVALID_REQUEST_BODY.getCode(), INVALID_REQUEST_BODY.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handle(HttpMediaTypeNotSupportedException exception) {
        logger.error("Request Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new BaseErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handle(Exception exception) {
        logger.error("Unexpected Ex:{}", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseErrorResponse(ERROR.getCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handle(AuthenticationException exception) {
        logger.error("Authentication Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
    }
}
