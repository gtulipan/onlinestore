package com.onlinestore.auth2.domain;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("role")
public class RoleEntity implements GrantedAuthority {

    @Id
    @Column("id")
    private UUID id;

    @Size(min = 1, max = 255)
    @Column("role_name")
    private String roleName;

    @CreatedDate
    @Column("created_date")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column("last_modified_date")
    private Timestamp lastModifiedDate;

    @Version
    @Column("version")
    private int version;

    @Column("customer_id")
    private UUID customerId;

    @Override
    public String getAuthority() {
        return roleName;
    }

}
