package com.accountmanagement.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MANAGER_NOT_FOUND_ERROR("001", "Manager is not found.", 404),
    PRODUCT_NOT_FOUND_ERROR("002", "Product is not found.", 404),
    BILL_NOT_FOUND_ERROR("003", "Bill is not found.", 404),
    BILLS_NOT_FOUND_ERROR("004", "Bills is not found.", 404),
    MANAGER_LOCK_ERROR("005", "Manager already is locked.", 400),
    MANAGER_EMPTY_DEFAULT_CREDIT_LIMIT_ERROR("007", "Manager default max credit limit is not empty.", 400),
    MANAGER_ALREADY_REGISTERED_ERROR("008", "Manager already is registered.", 409);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
