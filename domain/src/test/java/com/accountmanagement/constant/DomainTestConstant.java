package com.accountmanagement.constant;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.model.enumeration.Role;
import com.accountmanagement.domain.product.model.ProductDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DomainTestConstant {

    public static final UUID MOCK_BILL_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b991");
    public static final UUID MOCK_PRODUCT_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b992");
    public static final String MOCK_PRODUCT_NAME = "TEST PRODUCT";
    public static final UUID MOCK_MANAGER_ID = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b993");
    public static final String MOCK_FIRST_NAME = "TEST FIRST NAME";
    public static final String MOCK_LAST_NAME = "TEST LAST NAME";
    public static final String MOCK_EMAIL = "test@gmail.com";
    public static final String MOCK_PASSWORD = "password";
    public static final String MOCK_TOKEN = "TEST TOKEN";
    public static final String MOCK_BILL_NO = "TR0001";

    public static ManagerDto buildManagerDto() {
        return ManagerDto.builder()
            .id(MOCK_MANAGER_ID)
            .firstName(MOCK_FIRST_NAME)
            .lastName(MOCK_LAST_NAME)
            .email(MOCK_EMAIL)
            .role(Role.USER)
            .maxCreditLimit(new BigDecimal("200"))
            .usedCreditLimit(BigDecimal.ZERO)
            .token(MOCK_TOKEN)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }

    public static ProductDto buildProductDto() {
        return ProductDto.builder()
            .id(MOCK_PRODUCT_ID)
            .productName(MOCK_PRODUCT_NAME)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }

    public static BillDto buildBillDto() {
        return BillDto.builder()
            .id(MOCK_BILL_ID)
            .manager(buildManagerDto())
            .product(buildProductDto())
            .amount(BigDecimal.TEN)
            .billNo(MOCK_BILL_NO)
            .status(BillStatus.APPROVED)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    }
}
