package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDto(
        BigDecimal total,
        List<ItemDto> items) {

    public Order toOrder() {
        Order order = new Order();
        order.setTotal(total);
        order.setItems(getItems(items));
        return order;
    }

    public Order toOrder(Order order) {
        order.setTotal(total);
        order.setItems(getItems(items));
        return order;
    }

    private List<Item> getItems(List<ItemDto> dtos) {
        return dtos.stream().map(Item::new).collect(Collectors.toList());
    }

}
