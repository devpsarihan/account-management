package com.accountmanagement.domain.manager.usecase;

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
public class UpdateManagerUsedCreditLimitUseCase implements UseCase, Serializable {

    private UUID id;
    private BigDecimal usedCreditLimit;
}
