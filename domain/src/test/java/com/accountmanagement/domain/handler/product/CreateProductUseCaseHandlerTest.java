package com.accountmanagement.domain.handler.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static com.accountmanagement.constant.DomainTestConstant.*;

import com.accountmanagement.domain.adapter.ProductFakeAdapter;
import com.accountmanagement.domain.product.handler.CreateProductUseCaseHandler;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import java.util.UUID;
import org.junit.Test;

public class CreateProductUseCaseHandlerTest {

    private final ProductFakeAdapter productFakeAdapter = new ProductFakeAdapter();
    private final CreateProductUseCaseHandler useCaseHandler = new CreateProductUseCaseHandler(productFakeAdapter);

    @Test
    public void testHandle_WhenGivenUseCase_ShouldReturnProductId() {
        CreateProductUseCase createProductUseCase = CreateProductUseCase.builder()
            .productName(MOCK_PRODUCT_NAME)
            .build();
        UUID productId = useCaseHandler.handle(createProductUseCase);
        assertNotNull(productId);
        assertEquals(MOCK_PRODUCT_ID, productId);
    }
}
