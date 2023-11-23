package com.accountmanagement.domain.adapter;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_BILL_ID;
import static com.accountmanagement.constant.DomainTestConstant.buildBillDto;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.port.BillPort;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BillFakeAdapter implements BillPort {

    private final Map<UUID, BillDto> bills = new HashMap<>();

    @Override
    public BillDto createBill(final CreateBillUseCase useCase, BillStatus status) {
        BillDto billDto = buildBillDto();
        bills.put(billDto.getId(), billDto);
        return billDto;
    }

    @Override
    public List<BillDto> getAllByManagerIdAndBillStatus(final GetAllByManagerIdAndBillStatusUseCase useCase) {
        BillDto billDto = bills.get(MOCK_BILL_ID);
        return List.of(billDto);
    }
}

