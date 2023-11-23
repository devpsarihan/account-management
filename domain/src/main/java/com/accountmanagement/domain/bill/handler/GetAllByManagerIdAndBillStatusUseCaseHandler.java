package com.accountmanagement.domain.bill.handler;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.port.BillPort;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.common.util.DomainComponent;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetAllByManagerIdAndBillStatusUseCaseHandler implements
    UseCaseHandler<List<BillDto>, GetAllByManagerIdAndBillStatusUseCase> {

    private final BillPort billPort;

    @Override
    public List<BillDto> handle(final GetAllByManagerIdAndBillStatusUseCase useCase) {
        return billPort.getAllByManagerIdAndBillStatus(useCase);
    }
}
