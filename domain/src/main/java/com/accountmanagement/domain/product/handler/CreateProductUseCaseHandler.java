package com.accountmanagement.domain.product.handler;

import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.common.util.DomainComponent;
import com.accountmanagement.domain.product.port.ProductPort;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class CreateProductUseCaseHandler implements UseCaseHandler<UUID, CreateProductUseCase> {

    private final ProductPort productPort;

    @Override
    public UUID handle(final CreateProductUseCase useCase) {
        return productPort.createProduct(useCase);
    }
}
