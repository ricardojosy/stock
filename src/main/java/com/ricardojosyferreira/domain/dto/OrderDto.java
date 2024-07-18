package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDto(
        Long id,
        BigDecimal total,
        List<ItemDto> items) {

    public Order toOrder() {
        Order order = new Order();
        order.setTotal(total);
        order.setItems(getItems(items));
        return order;
    }

    public Order toOrder(Order order) {
        order.setTotal(getTotalOrder(items));
        return order;
    }

    private BigDecimal getTotalOrder(List<ItemDto> dtos) {
        double x = dtos.stream().mapToDouble(item -> item.price().doubleValue()).sum();
        return BigDecimal.valueOf(x);
    }

    private List<Item> getItems(List<ItemDto> dtos) {
        return dtos.stream().map(Item::new).collect(Collectors.toList());
    }

}
