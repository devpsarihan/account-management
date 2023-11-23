package com.accountmanagement.infra.adapter.product.rest.v1;

import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import com.accountmanagement.infra.adapter.product.rest.model.request.CreateProductRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final UseCaseHandler<UUID, CreateProductUseCase> createProductUseCaseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.of(Optional.of(createProductUseCaseHandler.handle(request.toModel())));
    }

}
