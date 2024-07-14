package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;

import java.math.BigDecimal;

public record ItemDto(
     Long productId,
     Long orderId,
     BigDecimal price,
     BigDecimal total,
     Integer quantity) {

}
