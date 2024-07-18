package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(Long id, BigDecimal total, Instant date, List<ItemDto> items) {
    public static OrderResponseDto toDto(Order order) {
        List<ItemDto> itemDtos = order.getItems().stream().map(item -> {
                    return new ItemDto(
                            item.getId(),
                            item.getProduct(),
                            item.getPrice(),
                            item.getTotal(),
                            item.getQuantity(),
                            order.getId(),
                            item.getCreateAt());
                }
        ).toList();
        return new OrderResponseDto(order.getId(), order.getTotal(), order.getCreateAt(), itemDtos);
    }
}
