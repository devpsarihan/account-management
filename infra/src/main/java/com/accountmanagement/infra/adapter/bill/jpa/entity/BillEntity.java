package com.accountmanagement.infra.adapter.bill.jpa.entity;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.infra.adapter.manager.jpa.entity.ManagerEntity;
import com.accountmanagement.infra.adapter.product.jpa.entity.ProductEntity;
import com.accountmanagement.infra.common.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BILL", schema = "ACCOUNT_MANAGEMENT", indexes = {
    @Index(name = "MANAGER_ID_INDEX", columnList = "MANAGER_ID, STATUS")})
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BillEntity extends BaseEntity implements Serializable {

    @Column(name = "MANAGER_ID", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID managerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ManagerEntity managerEntity;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "PRODUCT_ID", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID productId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ProductEntity productEntity;

    @Column(name = "BILL_NO", nullable = false)
    private String billNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private BillStatus status;

    public BillDto toModel(){
        return BillDto.builder()
            .id(super.getId())
            .manager(this.managerEntity.toModel())
            .product(this.productEntity.toModel())
            .amount(this.amount)
            .billNo(this.billNo)
            .status(status)
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }
}
