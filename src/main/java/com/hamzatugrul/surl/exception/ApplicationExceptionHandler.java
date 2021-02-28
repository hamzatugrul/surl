package com.hamzatugrul.surl.exception;

import com.hamzatugrul.surl.model.BaseResponse;
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
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(InvalidURLException exception) {
        logger.error("URL Validation Exception:{0}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_URL.getCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(KeyNotFoundException exception) {
        logger.error("Key Validation Exception:{0}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_URL.getCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(URISyntaxException exception) {
        logger.error("Request Validation Exception:{0}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_LONG_URL.getCode(), INVALID_LONG_URL.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(MethodArgumentNotValidException exception) {
        logger.error("Method Validation Exception:{0}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(INVALID_PARAMS.getCode(), exception.getBindingResult().getFieldErrors().toString()));
        //.body(new BaseResponse(INVALID_PARAMS.getCode(), INVALID_PARAMS.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(HttpMessageNotReadableException exception) {
        logger.error("Request Validation Exception:{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_REQUEST_BODY.getCode(), INVALID_REQUEST_BODY.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(HttpMediaTypeNotSupportedException exception) {
        logger.error("Request Validation Exception:{0}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new BaseResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(Exception exception) {
        logger.error("Unexpected Ex:{0}", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(ERROR.getCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(AuthenticationException exception) {
        logger.error("Authentication Exception:{}", exception.getMessage());
        return new ResponseEntity<>(new BaseResponse(exception.getMessage(),
                ResponseStatus.AUTHENTICATION_ERROR.getValue()), HttpStatus.UNAUTHORIZED);
    }*/



/*

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(URISyntaxException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Invalid Url format.", BaseResponse.BAD_REQUEST));
    }*/


}
