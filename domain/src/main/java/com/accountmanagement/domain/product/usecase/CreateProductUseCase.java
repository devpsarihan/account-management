package com.accountmanagement.domain.product.usecase;

import com.accountmanagement.domain.common.usecase.UseCase;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductUseCase implements UseCase, Serializable {

    private String productName;
}
