package com.accountmanagement.infra.adapter.manager.jpa.repository;

import com.accountmanagement.infra.adapter.manager.jpa.entity.ManagerEntity;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, UUID> {

    Optional<ManagerEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE ManagerEntity m SET m.usedCreditLimit = :usedCreditLimit WHERE m.id = :id")
    void updateManagerUsedCreditLimit(@Param("usedCreditLimit") BigDecimal usedCreditLimit, @Param("id") UUID id);
}
