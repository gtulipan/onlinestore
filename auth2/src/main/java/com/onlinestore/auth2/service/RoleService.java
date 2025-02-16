package com.onlinestore.auth2.services;

import com.onlinestore.auth2.domain.RoleEntity;
import com.onlinestore.auth2.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Mono<RoleEntity> saveRole(RoleEntity role) {
        // Beállítjuk a létrehozás és frissítés időbélyegeit
        if (role.getId() == null) {
            role.setId(UUID.randomUUID());
            role.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }
        role.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        return roleRepository.save(role);
    }

    public Mono<RoleEntity> updateRole(RoleEntity role) {
        // Frissítjük a módosítás időbélyegét
        role.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        return roleRepository.save(role);
    }
}
