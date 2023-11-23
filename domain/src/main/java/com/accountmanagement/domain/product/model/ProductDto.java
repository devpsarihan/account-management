package com.accountmanagement.domain.product.model;

import com.accountmanagement.domain.common.model.BaseDto;
import java.io.Serializable;
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
public class ProductDto extends BaseDto implements Serializable {

    private String productName;
}
