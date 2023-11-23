package com.accountmanagement.domain.bill.handler;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.model.payload.BillPayload;
import com.accountmanagement.domain.bill.port.BillEventProducerPort;
import com.accountmanagement.domain.bill.port.BillPort;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.common.util.DomainComponent;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.port.ManagerPort;
import com.accountmanagement.domain.manager.port.ManagerRedisLockPort;
import com.accountmanagement.domain.manager.usecase.GetManagerByIdUseCase;
import com.accountmanagement.domain.manager.usecase.UpdateManagerUsedCreditLimitUseCase;
import com.accountmanagement.domain.product.port.ProductPort;
import com.accountmanagement.domain.product.usecase.ExistsProductByIdUseCase;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class CreateBillUseCaseHandler implements UseCaseHandler<BillDto, CreateBillUseCase> {

    private final ManagerPort managerPort;
    private final ProductPort productPort;
    private final BillPort billPort;
    private final ManagerRedisLockPort managerRedisLockPort;
    private final BillEventProducerPort billEventProducerPort;

    @Override
    public BillDto handle(final CreateBillUseCase useCase) {
        ExistsProductByIdUseCase existsProductByIdUseCase = ExistsProductByIdUseCase.builder().id(useCase.getProductId()).build();
        if (!productPort.existsProductById(existsProductByIdUseCase)) {
            throw new AccountManagementException(ErrorCode.PRODUCT_NOT_FOUND_ERROR);
        }
        GetManagerByIdUseCase getManagerByIdUseCase = GetManagerByIdUseCase.builder().id(useCase.getManagerId()).build();
        ManagerDto manager = managerPort.getManagerById(getManagerByIdUseCase);
        managerRedisLockPort.lock(manager.getId());
        BillStatus status = checkManagerMaxCreditLimit(useCase.getAmount(), manager);
        BillDto bill = billPort.createBill(useCase, status);
        try {
            if (BillStatus.APPROVED.equals(status)) {
                UpdateManagerUsedCreditLimitUseCase updateManagerUsedCreditLimitUseCase = UpdateManagerUsedCreditLimitUseCase.builder()
                    .id(useCase.getManagerId())
                    .usedCreditLimit(manager.getUsedCreditLimit().add(useCase.getAmount()))
                    .build();
                managerPort.updateManagerUsedCreditLimit(updateManagerUsedCreditLimitUseCase);
            } else {
                BillPayload billPayload = BillPayload.builder().billId(bill.getId()).status(bill.getStatus()).build();
                billEventProducerPort.send(billPayload);
            }
        } finally {
            managerRedisLockPort.unlock(manager.getId());
        }
        return bill;
    }

    private BillStatus checkManagerMaxCreditLimit(BigDecimal amount, ManagerDto manager) {
        return manager.getMaxCreditLimit().compareTo(manager.getUsedCreditLimit().add(amount)) >= 0
            ? BillStatus.APPROVED : BillStatus.REJECTED;
    }
}
