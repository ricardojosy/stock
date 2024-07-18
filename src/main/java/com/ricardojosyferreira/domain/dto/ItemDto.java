package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ItemDto(
        Long id,
        Product product,
        BigDecimal price,
        BigDecimal total,
        Integer quantity,
        Long orderId,
        Instant createAt) {

    public Item toItem(ItemDto dto) {
        Item item = new Item();
        item.setId(dto.id);
        item.setQuantity(dto.quantity);
        item.setProduct(dto.product);
        item.setPrice(dto.price);
        item.setTotal(dto.total);
        Order order = new Order();
        order.setId(dto.orderId);
        item.setOrder(order);
        item.setCreateAt(dto.createAt);
        return item;
    }

    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getProduct(), item.getPrice(), item.getTotal(), item.getQuantity(), item.getOrder().getId(), item.getCreateAt());
    }

}
