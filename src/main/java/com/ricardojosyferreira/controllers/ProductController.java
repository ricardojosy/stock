package com.ricardojosyferreira.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ricardojosyferreira.domain.Product;
import com.ricardojosyferreira.domain.dto.ProductDto;
import com.ricardojosyferreira.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')" )
	public ResponseEntity<Page<Product>> listProducts(Pageable pageable) throws InterruptedException {
		System.out.println("pageNumber >>>>>> "+ pageable.getPageNumber());
		System.out.println("pageSize >>>>>> "+ pageable.getPageSize());

		return new ResponseEntity<>(productService.getPageOfProducts(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')" )
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) throws InterruptedException {
		Product product = productService.getProduct(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<Product> createProduct(@RequestBody @Validated ProductDto dto) {
		return ResponseEntity.ok(productService.createProductByController(dto));
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody @Validated ProductDto dto) throws InterruptedException {
		Product product = productService.getProduct(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productService.updateProductByController(dto.toProduct(product)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) throws InterruptedException {
		Product product = productService.getProduct(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		productService.deleteProductByController(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/queue")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<String> createProductQueue(@RequestBody @Validated ProductDto dto) throws JsonProcessingException {
		return ResponseEntity.ok(productService.sendProductToQueue(dto));
	}

}