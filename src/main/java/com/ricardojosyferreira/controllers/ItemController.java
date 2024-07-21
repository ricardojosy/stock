package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.dto.ItemDto;
import com.ricardojosyferreira.domain.dto.OrderDto;
import com.ricardojosyferreira.domain.dto.OrderResponseDto;
import com.ricardojosyferreira.services.ItemService;
import com.ricardojosyferreira.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://528f-2804-229c-8200-22da-aa51-5f99-917a-d62c.ngrok-free.app/"})
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    private BigDecimal totalOrder = BigDecimal.ZERO;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Item> addItem(@RequestBody @Validated ItemDto dto) {
        Item item = itemService.addItem(dto);
        updateTotalOrder(dto.orderId());
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{itemId}/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> updateItem(@PathVariable("itemId") Long itemId, @PathVariable("orderId") Long orderId, @RequestBody @Validated ItemDto dto) {
        Optional<Item> item = itemService.getItem(itemId);
        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        itemService.updateItem(dto.toItem(dto));
        updateTotalOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemId") Long itemId, @PathVariable("orderId") Long orderId) {
        itemService.delete(itemId);
        updateTotalOrder(orderId);
        return ResponseEntity.ok().build();
    }

    private void updateTotalOrder(Long orderId) {
        Order order = orderService.getOrder(orderId);
        OrderDto dto = order.toDto(order);
        totalOrder = getTotalOrder(dto);
        orderService.updateOrder(dto.toOrder(order), totalOrder);
    }

    private BigDecimal getTotalOrder(OrderDto dto) {
        totalOrder = BigDecimal.ZERO;
        dto.items().forEach(item -> totalOrder = totalOrder.add(item.total()));
        return totalOrder;
    }


}