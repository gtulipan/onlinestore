package com.onlinestore.auth2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID id;

    @Size(min = 1, max = 255)
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Version
    @Column(name = "version")
    private int version;

//FIXME:
//    @ManyToOne(fetch = FetchType.LAZY) R2dbc-ben nincs many-to-one és társai :( Utánna kell nézni. megírni
//    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
