package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.dto.OrderDto;
import com.ricardojosyferreira.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://528f-2804-229c-8200-22da-aa51-5f99-917a-d62c.ngrok-free.app/"})
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')" )
	public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) throws InterruptedException {
		Order order = orderService.getOrder(id);
		if (order == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(order);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<Order> createOrder(@RequestBody @Validated OrderDto dto) {
		return ResponseEntity.ok(orderService.createOrder(dto));
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody @Validated OrderDto dto) throws InterruptedException {
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