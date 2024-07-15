package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.Product;

import java.math.BigDecimal;

public record ItemResponseDto(
        Long id,
        Product product,
        BigDecimal price,
        BigDecimal total,
        Integer quantity) {

}
