package com.onlinestore.auth2.repositories;

import com.onlinestore.auth2.domain.RoleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface RoleRepository extends R2dbcRepository<RoleEntity, UUID> {
    Flux<RoleEntity> findByCustomerId(UUID customerId);
}
