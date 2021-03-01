package com.hamzatugrul.surl.model;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */
public class BaseErrorResponse {

    protected Integer code;
    protected String message;

    public BaseErrorResponse() {
    }

    public BaseErrorResponse(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
