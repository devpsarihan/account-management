package com.accountmanagement.domain.adapter;

import com.accountmanagement.domain.bill.model.payload.BillPayload;
import com.accountmanagement.domain.bill.port.BillEventProducerPort;
import lombok.extern.slf4j.Slf4j;

public class BillEventProducerFakeAdapter implements BillEventProducerPort {

    @Override
    public void send(BillPayload billPayload) {
    }
}
