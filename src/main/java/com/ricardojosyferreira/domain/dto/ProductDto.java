package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;

import java.math.BigDecimal;

public record ProductDto(
     String productName,
     String description,
     Category category,
     BigDecimal price,
     Boolean available,
     Integer quantity) {

    public Product toProduct() {
        return new Product(productName, description, category, price, available, quantity);
    }

    public Product toProduct(Product product) {
        product.setCategory(category);
        product.setProductName(productName);
        product.setDescription(description);
        product.setAvailable(available);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }
}
