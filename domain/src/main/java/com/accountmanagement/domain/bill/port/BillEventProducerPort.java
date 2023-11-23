package com.accountmanagement.domain.bill.port;

import com.accountmanagement.domain.bill.model.payload.BillPayload;

public interface BillEventProducerPort {

    void send(BillPayload billPayload);
}
