package com.accountmanagement.infra.adapter.bill;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.port.BillPort;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.model.enumeration.Role;
import com.accountmanagement.domain.product.model.ProductDto;
import com.accountmanagement.infra.adapter.bill.BillAdapter;
import com.accountmanagement.infra.adapter.bill.jpa.entity.BillEntity;
import com.accountmanagement.infra.adapter.bill.jpa.repository.BillRepository;
import com.accountmanagement.infra.adapter.manager.jpa.entity.ManagerEntity;
import com.accountmanagement.infra.adapter.product.jpa.entity.ProductEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillAdapterTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillAdapter billAdapter;

    private static final UUID MOCK_BILL_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b991");
    private static final UUID MOCK_PRODUCT_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b992");
    private static final UUID MOCK_MANAGER_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b993");
    private static final String MOCK_BILL_NO = "TR0001";

    @Test
    public void testCreateBill_WhenGivenValidBill_ShouldReturnBillDto() {
        CreateBillUseCase createBillUseCase = new CreateBillUseCase();
        createBillUseCase.setManagerId(MOCK_MANAGER_ID);
        createBillUseCase.setAmount(BigDecimal.TEN);
        createBillUseCase.setProductId(MOCK_PRODUCT_ID);
        createBillUseCase.setBillNo(MOCK_BILL_NO);

        when(billRepository.saveAndFlush(any(BillEntity.class))).thenReturn(createMockBillEntity());

        BillDto result = billAdapter.createBill(createBillUseCase, BillStatus.APPROVED);

        assertNotNull(result);
        assertEquals(MOCK_BILL_ID, result.getId());
        assertEquals(createBillUseCase.getManagerId(), result.getManager().getId());
        assertEquals(createBillUseCase.getAmount(), result.getAmount());
        assertEquals(createBillUseCase.getProductId(), result.getProduct().getId());
        assertEquals(createBillUseCase.getBillNo(), result.getBillNo());
        assertEquals(BillStatus.APPROVED, result.getStatus());

        verify(billRepository, times(1)).saveAndFlush(any(BillEntity.class));
        verify(entityManager, times(1)).refresh(any(BillEntity.class));
    }

    @Test
    public void testGetAllByManagerIdAndBillStatus_WhenGivenManagerIdAndBillStatus_ShouldReturnApprovedBills() {
        GetAllByManagerIdAndBillStatusUseCase getAllUseCase = new GetAllByManagerIdAndBillStatusUseCase();
        getAllUseCase.setManagerId(MOCK_MANAGER_ID);
        getAllUseCase.setStatus(BillStatus.APPROVED);

        when(billRepository.findAllByManagerIdAndStatus(any(), any())).thenReturn(Optional.of(Collections.singletonList(createMockBillEntity())));

        List<BillDto> result = billAdapter.getAllByManagerIdAndBillStatus(getAllUseCase);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        BillDto billDto = result.get(0);
        assertEquals(MOCK_BILL_ID, billDto.getId()); // Assuming you set the ID in your createMockBillEntity method
        assertEquals(getAllUseCase.getManagerId(), billDto.getManager().getId());
        assertEquals(BigDecimal.TEN, billDto.getAmount());
        assertEquals(MOCK_BILL_NO, billDto.getBillNo());
        assertEquals(BillStatus.APPROVED, billDto.getStatus());

        verify(billRepository, times(1)).findAllByManagerIdAndStatus(any(), any());
    }

    @Test
    public void testGetAllByManagerIdAndBillStatus_WhenGivenManagerIdAndBillStatus_ShouldReturnRejectedBills() {
        GetAllByManagerIdAndBillStatusUseCase getAllUseCase = new GetAllByManagerIdAndBillStatusUseCase();
        getAllUseCase.setManagerId(MOCK_MANAGER_ID);
        getAllUseCase.setStatus(BillStatus.REJECTED);

        BillEntity mockBillEntity = createMockBillEntity();
        mockBillEntity.setStatus(BillStatus.REJECTED);
        when(billRepository.findAllByManagerIdAndStatus(any(), any())).thenReturn(Optional.of(Collections.singletonList(
            mockBillEntity)));

        List<BillDto> result = billAdapter.getAllByManagerIdAndBillStatus(getAllUseCase);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        BillDto billDto = result.get(0);
        assertEquals(MOCK_BILL_ID, billDto.getId());
        assertEquals(getAllUseCase.getManagerId(), billDto.getManager().getId());
        assertEquals(BigDecimal.TEN, billDto.getAmount());
        assertEquals(MOCK_BILL_NO, billDto.getBillNo());
        assertEquals(BillStatus.REJECTED, billDto.getStatus());

        verify(billRepository, times(1)).findAllByManagerIdAndStatus(any(), any());
    }

    @Test
    public void testGetAllByManagerIdAndBillStatus_WhenGivenManagerIdAndBillStatus_ShouldThrowException() {
        GetAllByManagerIdAndBillStatusUseCase getAllUseCase = new GetAllByManagerIdAndBillStatusUseCase();
        getAllUseCase.setManagerId(UUID.randomUUID());
        getAllUseCase.setStatus(BillStatus.APPROVED);

        when(billRepository.findAllByManagerIdAndStatus(any(), any())).thenReturn(Optional.empty());

        AccountManagementException exception = assertThrows(AccountManagementException.class,
            () -> billAdapter.getAllByManagerIdAndBillStatus(getAllUseCase));

        assertEquals(ErrorCode.BILLS_NOT_FOUND_ERROR.getCode(), exception.getCode());

        verify(billRepository, times(1)).findAllByManagerIdAndStatus(any(), any());
    }


    private ProductEntity createMockProductEntity() {
        return ProductEntity.builder()
            .id(MOCK_PRODUCT_ID)
            .productName("TEST PRODUCT")
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }

    private ManagerEntity createMockManagerEntity() {
        return ManagerEntity.builder()
            .id(MOCK_MANAGER_ID)
            .firstName("TEST USER")
            .lastName("TEST LASTNAME")
            .email("test@gmail.com")
            .role(Role.USER)
            .maxCreditLimit(new BigDecimal("200"))
            .usedCreditLimit(BigDecimal.ZERO)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }

    private BillEntity createMockBillEntity(){
        return BillEntity.builder()
            .id(MOCK_BILL_ID)
            .managerId(MOCK_MANAGER_ID)
            .managerEntity(createMockManagerEntity())
            .productId(MOCK_PRODUCT_ID)
            .productEntity(createMockProductEntity())
            .amount(BigDecimal.TEN)
            .billNo(MOCK_BILL_NO)
            .status(BillStatus.APPROVED)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }
}
