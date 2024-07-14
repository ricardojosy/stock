package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Product;

import java.math.BigDecimal;

public record ItemChildDto(
        String key,
        Long id,
        Product product,
        BigDecimal price,
        BigDecimal total,
        Integer quantity) {
}
