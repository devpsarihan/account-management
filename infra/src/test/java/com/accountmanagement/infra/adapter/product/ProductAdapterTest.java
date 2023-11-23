package com.accountmanagement.infra.adapter.product;

import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import com.accountmanagement.domain.product.usecase.ExistsProductByIdUseCase;
import com.accountmanagement.infra.adapter.product.jpa.entity.ProductEntity;
import com.accountmanagement.infra.adapter.product.jpa.repository.ProductRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductAdapter productAdapter;

    @Test
    void testCreateProduct_WhenGivenValidProduct_ReturnProductId() {
        CreateProductUseCase createProductUseCase = CreateProductUseCase.builder().productName("Test Product").build();
        UUID id = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b992");
        ProductEntity productEntity = ProductEntity.builder()
            .id(id)
            .productName(createProductUseCase.getProductName())
            .build();
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        UUID productId = productAdapter.createProduct(createProductUseCase);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
        assertEquals(id, productId);
    }

    @Test
    void testExistsProductById_WhenGivenProductId_ShouldReturnTrue() {
        ExistsProductByIdUseCase existsProductByIdUseCase = new ExistsProductByIdUseCase(UUID.randomUUID());
        when(productRepository.existsById(any(UUID.class))).thenReturn(true);

        boolean exists = productAdapter.existsProductById(existsProductByIdUseCase);

        verify(productRepository, times(1)).existsById(any(UUID.class));
        assertTrue(exists);
    }

    @Test
    void testExistsProductById_WhenGivenProductId_ShouldReturnFalse() {
        ExistsProductByIdUseCase existsProductByIdUseCase = new ExistsProductByIdUseCase(UUID.randomUUID());
        when(productRepository.existsById(any(UUID.class))).thenReturn(false);

        boolean exists = productAdapter.existsProductById(existsProductByIdUseCase);

        verify(productRepository, times(1)).existsById(any(UUID.class));
        assertFalse(exists);
    }
}

