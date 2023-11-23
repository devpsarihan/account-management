package com.accountmanagement.infra.adapter.product.jpa.repository;

import com.accountmanagement.infra.adapter.product.jpa.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

}
