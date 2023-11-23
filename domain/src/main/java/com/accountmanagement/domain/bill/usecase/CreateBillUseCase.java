package com.accountmanagement.domain.bill.usecase;

import com.accountmanagement.domain.common.usecase.UseCase;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillUseCase implements UseCase, Serializable {

    private UUID managerId;
    private BigDecimal amount;
    private UUID productId;
    private String billNo;
}
