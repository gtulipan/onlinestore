package com.onlinestore.auth2.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("customer")
public class Customer {

    @Id
    @Column("id")
    private UUID id;

    @Size(min = 1, max = 255)
    @NotNull(message = "Name cannot be NULL")
    @Column("name")
    private String name;

    @Size(min = 5, max = 50)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email!")
    @NotNull(message = "Email cannot be NULL")
    @Column("email")
    private String email;

    @NotNull(message = "Password cannot be NULL")
    @Column("password")
    @Size(min = 1, max = 255)
    private String password;

    @Size(min = 9, max = 25)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
    @Column("phone_number")
    private String phoneNumber;

    @Size(min = 1, max = 255)
    @Column("address")
    private String address;

    @Column("status")
    private Status status;

    @CreatedDate
    @Column("created_date")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column("last_modified_date")
    private Timestamp lastModifiedDate;

    @Version
    private Integer version;

    //R2dbc-ben nincs many-to-one és társai. Manuálisan kell lekezelni.
    //@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleEntity> roles;
}
