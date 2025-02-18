package com.xuecheng.base.exception;

public class BusinessException extends RuntimeException{

    private String errCode;
    private String errMessage;

    public BusinessException() {
        super();
    }

    public BusinessException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
