package com.ricardojosyferreira.repositories;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOrderIdOrderById(Long orderId);
}
