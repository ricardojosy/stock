package com.ricardojosyferreira.listeners.dto;

import java.math.BigDecimal;

public record ProductCreateEvent(
        String productName,
        String description,
        BigDecimal price,
        Long categoryId,
        Boolean available,
        Integer quantity) {

}
