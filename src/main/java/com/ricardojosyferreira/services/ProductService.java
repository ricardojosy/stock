package com.ricardojosyferreira.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ricardojosyferreira.listeners.dto.ProductCreateEvent;
import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;
import com.ricardojosyferreira.domain.dto.ProductDto;
import com.ricardojosyferreira.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private RabbitService rabbitService;

	public Page<Product> getPageOfProducts(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Product getProduct(Long id) {
		Optional<Product> product = repository.findById(id);
		if (product.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return product.get();
	}

	public Product createProductByController(ProductDto dto) {
		return repository.save(dto.toProduct());
	}

	public Product updateProductByController(Product product) {
		return repository.save(product);
	}

	public void createPproductByListener(ProductCreateEvent event) {
		Product product = new Product();
		product.setProductName(event.productName());
		product.setDescription(event.description());
		product.setPrice(event.price());
		product.setAvailable(event.available());
		product.setQuantity(event.quantity());
		product.setCategory(new Category(event.categoryId()));
		repository.save(product);
	}

	public String sendProductToQueue(ProductDto dto) {
        try {
            rabbitService.sendProduct(dto);
			return "Product sent to queue";
        } catch (JsonProcessingException e) {
			return "Product not sent. Error: " + e.getMessage();
        }
    }

	public void deleteProductByController(Long id) {
		repository.deleteById(id);
	}
}
