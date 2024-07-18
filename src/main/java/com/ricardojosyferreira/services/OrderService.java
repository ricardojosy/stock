package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.Order;
import com.ricardojosyferreira.domain.Product;
import com.ricardojosyferreira.domain.dto.*;
import com.ricardojosyferreira.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order getOrder(Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return order.get();
    }

    public List<OrderResponseDto> getOrders() {
        List<Order> orders = repository.findAllByOrderById();
        return orders.stream().map(OrderResponseDto::toDto).toList();
    }

    public Order createOrder(OrderDto dto) {
        return repository.save(dto.toOrder());
    }

    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
