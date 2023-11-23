package com.accountmanagement.infra.adapter.bill.rest.model.request;

import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillRequest implements Serializable {

    @NotEmpty
    private UUID managerId;
    @NotEmpty
    @PositiveOrZero(message = "Amount should be positive value")
    private BigDecimal amount;
    @NotEmpty
    private UUID productId;
    @NotEmpty
    private String billNo;

    public CreateBillUseCase toModel(){
        return CreateBillUseCase.builder()
            .managerId(this.managerId)
            .amount(this.amount)
            .productId(this.productId)
            .billNo(this.billNo)
            .build();
    }
}
