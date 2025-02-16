package com.onlinestore.auth2.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    private UUID id;

    @Size(min = 1, max = 255)
    private String roleName;

    private OffsetDateTime createdDate;

    private OffsetDateTime lastModifiedDate;

    private int version;

    private UUID customerId;
}
