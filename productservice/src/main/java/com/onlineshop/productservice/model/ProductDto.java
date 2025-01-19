package com.onlineshop.productservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    @Null
    private String id;

    @NotBlank
    private String name;

    @Positive
    private double price;

    @NotBlank
    private String description;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private Integer version;
}
