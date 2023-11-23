package com.accountmanagement.infra.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.accountmanagement.infra.adapter.manager.rest.model.request.LoginManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.request.RegisterManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.response.ManagerResponse;
import com.accountmanagement.infra.adapter.product.rest.model.request.CreateProductRequest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ManagerControllerTest extends AccountManagementTestContainer{

    @Test
    void testRegisterManager(){
        RegisterManagerRequest request = new RegisterManagerRequest();
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        ResponseEntity<ManagerResponse> authResponseEntity =
            testRestTemplate.postForEntity("/v1/managers/register", request, ManagerResponse.class);
        ManagerResponse response = authResponseEntity.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, authResponseEntity.getStatusCode());
    }

    @Test
    void testLoginManager(){
        RegisterManagerRequest request = new RegisterManagerRequest();
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        testRestTemplate.postForEntity("/v1/managers/register", request, ManagerResponse.class);

        LoginManagerRequest loginRequest = new LoginManagerRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("password");
        ResponseEntity<ManagerResponse> loginResponseEntity =
            testRestTemplate.postForEntity("/v1/managers/login", loginRequest, ManagerResponse.class);
        ManagerResponse response = loginResponseEntity.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, loginResponseEntity.getStatusCode());
    }

}
