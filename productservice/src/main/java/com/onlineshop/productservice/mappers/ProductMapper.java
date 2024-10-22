package com.onlineshop.productservice.mappers;

import com.onlineshop.productservice.domain.Product;
import com.onlineshop.productservice.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);
    Product toProduct(ProductDto productDto);
}

