package com.accountmanagement.domain.common.exception;

import lombok.Data;

@Data
public class AccountManagementException extends RuntimeException {

    private final String code;
    private final String message;
    private final Integer httpStatusCode;

    public AccountManagementException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    public AccountManagementException(String code, String message, Integer httpStatusCode) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
