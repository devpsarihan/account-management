package com.accountmanagement.infra.adapter.bill.rest.model.response;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.infra.common.response.BaseResponse;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillResponse extends BaseResponse implements Serializable {

    private String firstName;
    private String lastName;
    private BigDecimal amount;
    private String productName;
    private String billNo;
    private BillStatus status;

    public static BillResponse toModel(BillDto bill) {
        return BillResponse.builder()
            .id(bill.getId())
            .firstName(bill.getManager().getFirstName())
            .lastName(bill.getManager().getLastName())
            .productName(bill.getProduct().getProductName())
            .amount(bill.getAmount())
            .billNo(bill.getBillNo())
            .status(bill.getStatus())
            .createDate(bill.getCreateDate())
            .updateDate(bill.getUpdateDate())
            .build();
    }
}
