package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.dto.CategoryDto;
import com.ricardojosyferreira.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://528f-2804-229c-8200-22da-aa51-5f99-917a-d62c.ngrok-free.app/"})
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> listCategories(Pageable pageable) throws InterruptedException {
        return new ResponseEntity<>(categoryService.getPageOfCategories(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) throws InterruptedException {
        Category category = categoryService.getCategory(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody @Validated CategoryDto dto) {
        return ResponseEntity.ok(categoryService.createCategoryByController(dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody @Validated CategoryDto dto) throws InterruptedException {
        Category category = categoryService.getCategory(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoryService.updateCategoryByController(dto.toCategory(category)));
    }


}
