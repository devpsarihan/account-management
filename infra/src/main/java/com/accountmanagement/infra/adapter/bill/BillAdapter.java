package com.accountmanagement.infra.adapter.bill;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.port.BillPort;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.infra.adapter.bill.jpa.entity.BillEntity;
import com.accountmanagement.infra.adapter.bill.jpa.repository.BillRepository;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillAdapter implements BillPort {

    private final EntityManager entityManager;
    private final BillRepository billRepository;

    @Transactional
    @Override
    public BillDto createBill(final CreateBillUseCase useCase, BillStatus status) {
        BillEntity bill = BillEntity.builder()
            .managerId(useCase.getManagerId())
            .amount(useCase.getAmount())
            .productId(useCase.getProductId())
            .billNo(useCase.getBillNo())
            .status(status)
            .build();
        BillEntity savedBill = billRepository.saveAndFlush(bill);
        entityManager.refresh(savedBill);
        return savedBill.toModel();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "bills", key = "{#useCase.managerId, #useCase.status}", cacheManager = "redisCacheManager", unless = "#result==null")
    @Override
    public List<BillDto> getAllByManagerIdAndBillStatus(final GetAllByManagerIdAndBillStatusUseCase useCase) {
        return billRepository.findAllByManagerIdAndStatus(useCase.getManagerId(), useCase.getStatus())
            .orElseThrow(() -> new AccountManagementException(ErrorCode.BILLS_NOT_FOUND_ERROR)).stream()
            .map(BillEntity::toModel)
            .toList();
    }
}
