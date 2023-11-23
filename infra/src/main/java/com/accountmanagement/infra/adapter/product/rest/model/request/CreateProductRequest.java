package com.accountmanagement.infra.adapter.product.rest.model.request;

import com.accountmanagement.domain.product.usecase.CreateProductUseCase;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest implements Serializable {

    @NotEmpty
    private String productName;

    public CreateProductUseCase toModel(){
        return CreateProductUseCase.builder()
            .productName(this.productName)
            .build();
    }
}
