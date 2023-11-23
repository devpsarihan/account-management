package com.accountmanagement.infra.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.accountmanagement.infra.adapter.manager.rest.model.request.RegisterManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.response.ManagerResponse;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Testcontainers
class AccountManagementTestContainer {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    static {
        GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
                .withExposedPorts(6379).withCommand("redis-server --save 20 1 --loglevel warning --requirepass eYVX7EwVmmxKPCDmwMtyKVge8oLd2t81");
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.connect-timeout", "60");
        System.setProperty("spring.redis.password", "eYVX7EwVmmxKPCDmwMtyKVge8oLd2t81");
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @Container
    public static MySQLContainer<?> mySqlDB =
        new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("ACCOUNT_MANAGEMENT")
            .withUsername("ACCOUNT-MANAGEMENT_USER")
            .withPassword("ACCOUNT-MANAGEMENT_PASSWORD");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlDB::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlDB::getUsername);
        registry.add("spring.datasource.password", mySqlDB::getPassword);
    }

    @NotNull
    HttpHeaders getHttpHeadersWithToken() {
        RegisterManagerRequest request = new RegisterManagerRequest();
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        ResponseEntity<ManagerResponse> managerResponse =
            testRestTemplate.postForEntity("/v1/managers/register", request, ManagerResponse.class);
        ManagerResponse response = managerResponse.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, managerResponse.getStatusCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getToken());
        return headers;
    }
}
