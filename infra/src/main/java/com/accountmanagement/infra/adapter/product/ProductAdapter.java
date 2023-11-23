package com.accountmanagement.infra.adapter.product;

import com.accountmanagement.domain.product.port.ProductPort;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import com.accountmanagement.domain.product.usecase.ExistsProductByIdUseCase;
import com.accountmanagement.infra.adapter.product.jpa.entity.ProductEntity;
import com.accountmanagement.infra.adapter.product.jpa.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductAdapter implements ProductPort {

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public UUID createProduct(final CreateProductUseCase useCase) {
        return productRepository.save(ProductEntity.builder()
                .productName(useCase.getProductName())
                .build())
            .getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "isProductExists", key = "#useCase.id", cacheManager = "redisCacheManager", unless = "#result==null")
    @Override
    public boolean existsProductById(final ExistsProductByIdUseCase useCase) {
        return productRepository.existsById(useCase.getId());
    }
}
