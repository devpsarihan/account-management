package com.accountmanagement.domain.handler.bill;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_BILL_NO;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_EMAIL;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_FIRST_NAME;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_LAST_NAME;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_MANAGER_ID;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_PASSWORD;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_PRODUCT_ID;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_PRODUCT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.accountmanagement.domain.adapter.BillEventProducerFakeAdapter;
import com.accountmanagement.domain.adapter.BillFakeAdapter;
import com.accountmanagement.domain.adapter.ManagerFakeAdapter;
import com.accountmanagement.domain.adapter.ManagerRedisLockFakeAdapter;
import com.accountmanagement.domain.adapter.ProductFakeAdapter;
import com.accountmanagement.domain.bill.handler.CreateBillUseCaseHandler;
import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import java.math.BigDecimal;
import org.junit.Test;

public class CreateBillUseCaseHandlerTest {

    private final ManagerFakeAdapter managerFakeAdapter = new ManagerFakeAdapter();
    private final ProductFakeAdapter productFakeAdapter = new ProductFakeAdapter();
    private final BillFakeAdapter billFakeAdapter = new BillFakeAdapter();
    private final ManagerRedisLockFakeAdapter managerRedisLockFakeAdapter = new ManagerRedisLockFakeAdapter();
    private final BillEventProducerFakeAdapter billEventProducerFakeAdapter = new BillEventProducerFakeAdapter();
    private final CreateBillUseCaseHandler useCaseHandler = new CreateBillUseCaseHandler(managerFakeAdapter,
        productFakeAdapter, billFakeAdapter, managerRedisLockFakeAdapter, billEventProducerFakeAdapter);

    @Test
    public void testHandle_WhenGivenUseCase_ShouldReturnBillDto() {
        RegisterManagerUseCase registerManagerUseCase = RegisterManagerUseCase.builder()
            .firstName(MOCK_FIRST_NAME)
            .lastName(MOCK_LAST_NAME)
            .email(MOCK_EMAIL)
            .password(MOCK_PASSWORD)
            .build();
        managerFakeAdapter.registerManager(registerManagerUseCase);

        CreateProductUseCase createProductUseCase = CreateProductUseCase.builder()
            .productName(MOCK_PRODUCT_NAME)
            .build();
        productFakeAdapter.createProduct(createProductUseCase);

        CreateBillUseCase useCase = CreateBillUseCase.builder()
            .managerId(MOCK_MANAGER_ID)
            .amount(BigDecimal.TEN)
            .productId(MOCK_PRODUCT_ID)
            .billNo(MOCK_BILL_NO)
            .build();
        BillDto billDto = useCaseHandler.handle(useCase);

        assertNotNull(billDto);
        assertEquals(useCase.getManagerId(), billDto.getManager().getId());
        assertEquals(useCase.getBillNo(), billDto.getBillNo());
    }

}
