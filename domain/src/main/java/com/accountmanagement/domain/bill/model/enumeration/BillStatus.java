package com.accountmanagement.domain.bill.model.enumeration;

public enum BillStatus {

    APPROVED("ONAYLANDI"),
    REJECTED("REDDEDİLDİ");

    String status;

    BillStatus(String status) {
        this.status = status;
    }
}
