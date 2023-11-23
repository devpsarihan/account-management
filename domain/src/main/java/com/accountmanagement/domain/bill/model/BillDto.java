package com.accountmanagement.domain.bill.model;

import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.common.model.BaseDto;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.product.model.ProductDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillDto extends BaseDto implements Serializable {

    private ManagerDto manager;
    private BigDecimal amount;
    private ProductDto product;
    private String billNo;
    private BillStatus status;
}
