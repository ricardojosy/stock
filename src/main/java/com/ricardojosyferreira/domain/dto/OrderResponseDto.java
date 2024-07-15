package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(Long id, BigDecimal total, Instant date, List<ItemResponseDto> items) {

    public static OrderResponseDto getTree(Order order) {
        List<ItemResponseDto> itemDtos = order.getItems().stream().map(item -> {
                    return new ItemResponseDto(
                            item.getId(),
                            item.getProduct(),
                            item.getPrice(),
                            item.getTotal(),
                            item.getQuantity());
                }
        ).toList();

        return new OrderResponseDto(order.getId(), order.getTotal(), order.getCreateAt(), itemDtos);
    }
}
