package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;
import com.ricardojosyferreira.domain.dto.ProductDto;
import com.ricardojosyferreira.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListProducts() throws InterruptedException {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("ProductName 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5));
        productList.add(new Product("ProductName 2", "ProductDesc 2", new Category(2L), BigDecimal.valueOf(100.0), true, 5));
        when(productService.getAll()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.listProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetProduct() throws InterruptedException {
        Long productId = 1L;
        Product product = new Product("Product 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productService.getProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product 1", response.getBody().getProductName());
    }

    @Test
    public void testCreateProduct() {
        ProductDto productDto = new ProductDto("New Product", "new ProductDesc", new Category(2L), BigDecimal.valueOf(250.0), true, 4 );
        Product createdProduct = new Product("New Product", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productService.createProductByController(any())).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Product", response.getBody().getProductName());
    }

    @Test
    public void testUpdateProduct() throws InterruptedException {
        Long productId = 1L;
        ProductDto productDto = new ProductDto("Updated Product", "Updated ProductDesc", new Category(2L), BigDecimal.valueOf(250.0), true, 4 );
        Product existingProduct = new Product("ProductName 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5);
        Product updatedProduct = new Product("Updated Product", "ProductDesc updated 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productService.getProduct(productId)).thenReturn(existingProduct);
        when(productService.updateProductByController(any())).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Product", response.getBody().getProductName());
    }

    @Test
    public void testDeleteProduct() throws InterruptedException {
        Long productId = 1L;
        Product existingProduct = new Product("ProductName 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productService.getProduct(productId)).thenReturn(existingProduct);

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).deleteProductByController(productId);
    }

    // Você pode adicionar mais testes conforme necessário para outros métodos

}
