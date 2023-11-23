package com.accountmanagement.domain.bill.port;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import java.util.List;

public interface BillPort {

    BillDto createBill(final CreateBillUseCase useCase, final BillStatus status);

    List<BillDto> getAllByManagerIdAndBillStatus(final GetAllByManagerIdAndBillStatusUseCase useCase);
}
