package com.hamzatugrul.surl.exception;

import com.hamzatugrul.surl.model.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URISyntaxException;

import static com.hamzatugrul.surl.enums.EnmUrlShortenerResults.INVALID_LONG_URL;
import static com.hamzatugrul.surl.enums.EnmUrlShortenerResults.INVALID_PARAMS;
import static com.hamzatugrul.surl.enums.EnmUrlShortenerResults.INVALID_REQUEST_BODY;
import static com.hamzatugrul.surl.enums.EnmUrlShortenerResults.INVALID_URL;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(URISyntaxException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_LONG_URL.getCode(), INVALID_LONG_URL.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(InvalidURLException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(INVALID_URL.getCode(), INVALID_URL.getMessage()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<BaseResponse> handle(MethodArgumentNotValidException exception) {
        //you will get all javax failed validation, can be more than one
        //so you can return the set of error messages or just the first message
        logger.error("Request Validation Exception:{}", exception.getMessage());
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
/*
    @ExceptionHandler(value = AuthenticationException.class)
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
