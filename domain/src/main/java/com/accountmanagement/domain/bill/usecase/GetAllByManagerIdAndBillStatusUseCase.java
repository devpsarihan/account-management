package com.accountmanagement.domain.bill.usecase;

import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.common.usecase.UseCase;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllByManagerIdAndBillStatusUseCase implements UseCase, Serializable {

    private UUID managerId;
    private BillStatus status;
}
