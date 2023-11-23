package com.accountmanagement.infra.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.accountmanagement.infra.adapter.bill.rest.model.request.CreateBillRequest;
import com.accountmanagement.infra.adapter.bill.rest.model.response.BillResponse;
import com.accountmanagement.infra.adapter.manager.rest.model.request.LoginManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.request.RegisterManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.response.ManagerResponse;
import com.accountmanagement.infra.adapter.product.rest.model.request.CreateProductRequest;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(
    partitions = 1,
    brokerProperties = {"listeners=PLAINTEXT://localhost:29092", "port=29092"},
    topics = "account-management")
public class BillControllerTest extends AccountManagementTestContainer{

    @Test
    public void testCreateBill(){
        RegisterManagerRequest request = new RegisterManagerRequest();
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        ResponseEntity<ManagerResponse> managerResponseEntity =
            testRestTemplate.postForEntity("/v1/managers/register", request, ManagerResponse.class);
        ManagerResponse managerResponse = managerResponseEntity.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(managerResponse.getToken());

        CreateProductRequest createProductRequest = CreateProductRequest.builder()
            .productName("TEST PRODUCT")
            .build();
        HttpEntity<CreateProductRequest> productRequest = new HttpEntity<>(createProductRequest, headers);
        ResponseEntity<UUID> productResponse = testRestTemplate.postForEntity("/v1/products", productRequest, UUID.class);

        CreateBillRequest createBillRequest = CreateBillRequest.builder()
            .managerId(managerResponse.getId())
            .amount(BigDecimal.TEN)
            .productId(productResponse.getBody())
            .billNo("TR0001")
            .build();

        HttpEntity<CreateBillRequest> billRequest = new HttpEntity<>(createBillRequest, headers);
        ResponseEntity<BillResponse> billResponse = testRestTemplate.postForEntity("/v1/bills",
            billRequest, BillResponse.class);

        assertNotNull(billResponse.getBody());
        assertEquals("TR0001", billResponse.getBody().getBillNo());
    }
}
