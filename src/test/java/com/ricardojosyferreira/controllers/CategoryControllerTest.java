package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.dto.CategoryDto;
import com.ricardojosyferreira.services.CategoryService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListCategories() throws InterruptedException {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category( "Category 1", 1L));
        categoryList.add(new Category( "Category 2", 2L));

        when(categoryService.getPageOfCategories(any())).thenReturn(new PageImpl<>(categoryList));

        ResponseEntity<Page<Category>> response = categoryController.listCategories(mock(Pageable.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetCategory() throws InterruptedException {
        Long categoryId = 1L;
        Category category = new Category("Category 1",1L);

        when(categoryService.getCategory(categoryId)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category 1", response.getBody().getCategoryName());
    }

    @Test
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto("New Category", 1L);
        Category createdCategory = new Category("New Category", 1L);

        when(categoryService.createCategoryByController(any())).thenReturn(createdCategory);

        ResponseEntity<Category> response = categoryController.createCategory(categoryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Category", response.getBody().getCategoryName());
    }

    @Test
    public void testUpdateCategory() throws InterruptedException {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("Updated Category", 1L);
        Category existingCategory = new Category("Category 1", 1L);
        Category updatedCategory = new Category("Updated Category", 1L);

        when(categoryService.getCategory(categoryId)).thenReturn(existingCategory);
        when(categoryService.updateCategoryByController(any())).thenReturn(updatedCategory);

        ResponseEntity<Category> response = categoryController.updateCategory(categoryId, categoryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Category", response.getBody().getCategoryName());
    }

    // Você pode adicionar mais testes conforme necessário para outros métodos

}
