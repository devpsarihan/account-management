package com.accountmanagement.domain.adapter;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_PRODUCT_ID;
import static com.accountmanagement.constant.DomainTestConstant.buildProductDto;

import com.accountmanagement.domain.product.model.ProductDto;
import com.accountmanagement.domain.product.port.ProductPort;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import com.accountmanagement.domain.product.usecase.ExistsProductByIdUseCase;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductFakeAdapter implements ProductPort {

    private final Map<UUID, ProductDto> products = new HashMap<>();

    @Override
    public UUID createProduct(final CreateProductUseCase useCase) {
        ProductDto productDto = buildProductDto();
        products.put(MOCK_PRODUCT_ID, productDto);
        return productDto.getId();
    }

    @Override
    public boolean existsProductById(final ExistsProductByIdUseCase useCase) {
        return products.containsKey(useCase.getId());
    }
}
