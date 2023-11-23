package com.accountmanagement.domain.handler.bill;

import com.accountmanagement.constant.DomainTestConstant;
import com.accountmanagement.domain.adapter.BillFakeAdapter;
import com.accountmanagement.domain.bill.handler.GetAllByManagerIdAndBillStatusUseCaseHandler;
import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

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

public class GetAllByManagerIdAndBillStatusUseCaseHandlerTest {

    private final BillFakeAdapter billFakeAdapter = new BillFakeAdapter();
    private final GetAllByManagerIdAndBillStatusUseCaseHandler useCaseHandler = new GetAllByManagerIdAndBillStatusUseCaseHandler(
        billFakeAdapter);

    @Test
    public void testHandle_WhenGivenUseCase_ShouldReturnBillDtoList() {
        CreateBillUseCase createBillUseCase = CreateBillUseCase.builder()
            .managerId(MOCK_MANAGER_ID)
            .amount(BigDecimal.TEN)
            .productId(MOCK_PRODUCT_ID)
            .billNo(MOCK_BILL_NO)
            .build();
        billFakeAdapter.createBill(createBillUseCase, BillStatus.APPROVED);

        GetAllByManagerIdAndBillStatusUseCase useCase = GetAllByManagerIdAndBillStatusUseCase.builder()
            .managerId(DomainTestConstant.MOCK_MANAGER_ID)
            .status(BillStatus.APPROVED)
            .build();
        List<BillDto> bills = useCaseHandler.handle(useCase);

        assertNotNull(bills);
        BillDto billDto = bills.get(0);
        assertEquals(DomainTestConstant.MOCK_MANAGER_ID, billDto.getManager().getId());
    }

}
