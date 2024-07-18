package com.ricardojosyferreira.listeners.dto;

import com.ricardojosyferreira.domain.Category;

import java.math.BigDecimal;

public record ProductCreateEvent(
        String productName,
        String description,
        BigDecimal price,
        Category category,
        Boolean available,
        Integer quantity) {

}
