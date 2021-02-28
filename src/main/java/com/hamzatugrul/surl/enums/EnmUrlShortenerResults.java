package com.hamzatugrul.surl.enums;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */
public enum EnmUrlShortenerResults {

    SUCCESS(0,"Success"),
    ERROR(1,"Unexpected Error!"),
    INVALID_REQUEST_BODY(100,"Required request body is missing"),
    INVALID_PARAMS(101, "Invalid parameters in request."),
    INVALID_URL(102, "URL is not valid."),
    INVALID_LONG_URL(103, "Provided URL is invalid!"),
    //URL_IN_BLACKLIST(103, "Long URL is in the blacklist"),

    NOT_EXIST(401, "Requested resource does not exist");

    private Integer code;
    private String message;

    EnmUrlShortenerResults(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
