package com.accountmanagement.infra.adapter.bill.jpa.repository;

import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.infra.adapter.bill.jpa.entity.BillEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, UUID> {

    Optional<List<BillEntity>> findAllByManagerIdAndStatus(UUID managerId, BillStatus status);
}
