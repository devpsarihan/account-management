package com.accountmanagement.domain.product.port;

import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import com.accountmanagement.domain.product.usecase.ExistsProductByIdUseCase;
import java.util.UUID;

public interface ProductPort {

    UUID createProduct(final CreateProductUseCase useCase);

    boolean existsProductById(final ExistsProductByIdUseCase useCase);
}
