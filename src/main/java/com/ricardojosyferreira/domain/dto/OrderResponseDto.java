package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(String key, BigDecimal total, Instant date) {

    private static Integer index = 0;
//        return new OrderWithNoChildrenResponseDto(order.getId().toString(), order.getTotal(), order.getCreateAt());

    public static TreeResponseDto getTree(Order order) {
        return new TreeResponseDto(new OrderResponseDto(order.getId().toString(), order.getTotal(), order.getCreateAt()), getChildren(order));
    }
    public static List<ItemChildDto> getChildren(Order order) {
        index = 1;
        return order.getItems().stream().map(item -> {
                    String key = order.getId() + "-" + index++;
                    return new ItemChildDto(
                            key,
                            item.getId(),
                            item.getProduct(),
                            item.getPrice(),
                            item.getTotal(),
                            item.getQuantity());
                }
        ).toList();
    }
}
