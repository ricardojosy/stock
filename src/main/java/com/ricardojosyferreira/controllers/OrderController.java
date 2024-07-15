package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.dto.OrderDto;
import com.ricardojosyferreira.domain.dto.OrderResponseDto;
import com.ricardojosyferreira.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://528f-2804-229c-8200-22da-aa51-5f99-917a-d62c.ngrok-free.app/"})
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getListOrders() {
        List<OrderResponseDto> dtos = orderService.getOrders();
        if (dtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((OrderResponseDto.getTree(order)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Order> createOrder(@RequestBody @Validated OrderDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody @Validated OrderDto dto) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.updateOrder(dto.toOrder(order)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

}