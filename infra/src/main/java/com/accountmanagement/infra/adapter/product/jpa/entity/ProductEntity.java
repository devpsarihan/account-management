package com.accountmanagement.infra.adapter.product.jpa.entity;

import com.accountmanagement.domain.product.model.ProductDto;
import com.accountmanagement.infra.common.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT", schema = "ACCOUNT_MANAGEMENT", uniqueConstraints = @UniqueConstraint(columnNames = {
    "PRODUCT_NAME"}))
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity implements Serializable {

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    public ProductDto toModel() {
        return ProductDto.builder()
            .id(super.getId())
            .productName(this.productName)
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }
}
