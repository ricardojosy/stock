package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.Item;
import com.ricardojosyferreira.domain.dto.ItemDto;
import com.ricardojosyferreira.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public List<ItemDto> getItems(Long orderId) {
        List<Item> items = repository.findByOrderIdOrderById(orderId);
        return items.stream().map(ItemDto::toDto).toList();
    }

    public Item addItem(ItemDto dto) {
        return repository.save(dto.toItem(dto));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<Item> getItem(Long itemId) {
        return repository.findById(itemId);
    }

    public void updateItem(Item item) {
        repository.save(item);
    }
}
