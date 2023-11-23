package com.accountmanagement.infra.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.accountmanagement.infra.adapter.product.rest.model.request.CreateProductRequest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerTest extends AccountManagementTestContainer {

    @Test
    void testCreateProduct(){
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
            .productName("TEST PRODUCT")
            .build();

        HttpHeaders headers = getHttpHeadersWithToken();
        HttpEntity<CreateProductRequest> request = new HttpEntity<>(createProductRequest, headers);
        ResponseEntity<UUID> response = testRestTemplate.postForEntity("/v1/products", request, UUID.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
