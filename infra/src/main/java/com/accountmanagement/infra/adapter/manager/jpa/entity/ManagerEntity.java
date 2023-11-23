package com.accountmanagement.infra.adapter.manager.jpa.entity;

import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.model.enumeration.Role;
import com.accountmanagement.infra.common.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MANAGER", schema = "ACCOUNT_MANAGEMENT",
    indexes = @Index(name = "MANAGER_EMAIL_INDEX", columnList = "EMAIL"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL"}))
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ManagerEntity extends BaseEntity implements UserDetails, Serializable {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MAX_CREDIT_LIMIT", nullable = false)
    private BigDecimal maxCreditLimit;

    @Column(name = "USED_CREDIT_LIMIT", nullable = false)
    @ColumnDefault("0.00")
    private BigDecimal usedCreditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @PrePersist
    public void prePersist() {
        if (usedCreditLimit == null) {
            usedCreditLimit = BigDecimal.valueOf(0.00);
        }
    }

    public ManagerDto toModel() {
        return ManagerDto.builder()
            .id(super.getId())
            .firstName(this.firstName)
            .lastName(this.lastName)
            .email(this.email)
            .role(this.role)
            .maxCreditLimit(this.maxCreditLimit)
            .usedCreditLimit(this.usedCreditLimit)
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
