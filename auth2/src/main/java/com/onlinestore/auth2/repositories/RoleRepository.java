package com.onlinestore.auth2.repositories;

import com.onlinestore.auth2.domain.RoleEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RoleRepository extends R2dbcRepository<RoleEntity, UUID> {
    @Query("SELECT * FROM role WHERE role.customer_id = :customerId")
    Flux<RoleEntity> findByCustomerId(@Param("customerId") UUID customerId);
}
