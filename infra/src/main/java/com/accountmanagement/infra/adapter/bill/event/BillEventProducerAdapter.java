package com.accountmanagement.infra.adapter.bill.event;

import com.accountmanagement.domain.bill.model.payload.BillPayload;
import com.accountmanagement.domain.bill.port.BillEventProducerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillEventProducerAdapter implements BillEventProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.account-management}")
    private String accountManagementTopic;

    @Override
    public void send(BillPayload billPayload) {
        log.info("Send account management topic billId : {} and status : {}", billPayload.getBillId(),
            billPayload.getStatus());
        kafkaTemplate.send(accountManagementTopic, billPayload);
    }
}
