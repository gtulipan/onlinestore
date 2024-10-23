package com.onlineshop.productservice.mappers;

import com.onlineshop.productservice.domain.Product;
import com.onlineshop.productservice.model.ProductDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T07:28:30+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
public class ProductMapperImpl implements ProductMapper {

    private final DateMapper dateMapper = new DateMapper();

    @Override
    public ProductDto toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        if ( product.getId() != null ) {
            productDto.id( String.valueOf( product.getId() ) );
        }
        productDto.name( product.getName() );
        productDto.price( product.getPrice() );
        productDto.createdDate( dateMapper.asOffsetDateTime( product.getCreatedDate() ) );

        return productDto.build();
    }

    @Override
    public Product toProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        if ( productDto.getId() != null ) {
            product.id( Long.parseLong( productDto.getId() ) );
        }
        product.name( productDto.getName() );
        product.price( productDto.getPrice() );
        product.createdDate( dateMapper.asTimestamp( productDto.getCreatedDate() ) );

        return product.build();
    }
}
