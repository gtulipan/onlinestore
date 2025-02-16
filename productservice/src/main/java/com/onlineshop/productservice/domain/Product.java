package com.onlineshop.productservice.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
public class Product {

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "Name cannot be NULL")
    @Column("name")
    @Size(min = 1, max = 255)
    private String name;

    @Column
    @Positive
    private double price;

    @CreatedDate
    @Column("created_date")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column("last_modified_date")
    private Timestamp lastModifiedDate;

    @Column("description")
    @Size(max = 1024)
    private String description;

    @Version
    private Integer version;
}
