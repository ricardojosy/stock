package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;
import com.ricardojosyferreira.domain.dto.ProductDto;
import com.ricardojosyferreira.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RabbitService rabbitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPageOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5));
        productList.add(new Product("Product 2", "ProductDesc 2", new Category(2L), BigDecimal.valueOf(100.0), true, 5));

        Page<Product> page = new PageImpl<>(productList);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Product> result = productService.getPageOfProducts(mock(Pageable.class));

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetProduct_Success() {
        Long productId = 1L;
        Product product = new Product("Product 2", "ProductDesc 2", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProduct(productId);

        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void testGetProduct_NotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.getProduct(productId));
    }

    @Test
    public void testCreateProductByController() {
        ProductDto productDto = new ProductDto("New Product", "New ProductDesc", new Category(2L), BigDecimal.valueOf(250.0), true, 4 );
        Product createdProduct = new Product("New Product", "New ProductDesc", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productRepository.save(any())).thenReturn(createdProduct);

        Product result = productService.createProductByController(productDto);

        assertEquals("New Product", result.getProductName());
    }

    @Test
    public void testUpdateProductByController() {
        Long productId = 1L;
        Product product = new Product("Updated Product", "Updated ProductDesc", new Category(2L), BigDecimal.valueOf(100.0), true, 5);

        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.updateProductByController(product);

        assertEquals("Updated Product", result.getProductName());
    }

    @Test
    public void testDeleteProductByController() {
        Long productId = 1L;

        productService.deleteProductByController(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testGetProductsByNameStartWith() {
        String productName = "Product";

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5));
        productList.add(new Product("Product 2", "ProductDesc 2", new Category(2L), BigDecimal.valueOf(100.0), true, 5));

        when(productRepository.findByProductNameStartingWithIgnoreCase(productName)).thenReturn(productList);

        List<Product> result = productService.getProductsByNameStartWith(productName);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", "ProductDesc 1", new Category(2L), BigDecimal.valueOf(100.0), true, 5));
        productList.add(new Product("Product 2", "ProductDesc 2", new Category(2L), BigDecimal.valueOf(100.0), true, 5));

        when(productRepository.findAllByOrderByProductName()).thenReturn(productList);

        List<Product> result = productService.getAll();

        assertEquals(2, result.size());
    }

}
