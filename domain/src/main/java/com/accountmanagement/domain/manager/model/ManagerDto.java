package com.accountmanagement.domain.manager.model;

import com.accountmanagement.domain.common.model.BaseDto;
import com.accountmanagement.domain.manager.model.enumeration.Role;
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
public class ManagerDto extends BaseDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal maxCreditLimit;
    private BigDecimal usedCreditLimit;
    private Role role;
    private String token;
}
