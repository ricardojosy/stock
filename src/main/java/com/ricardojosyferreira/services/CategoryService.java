package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.dto.CategoryDto;
import com.ricardojosyferreira.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public Page<Category> getPageOfCategories(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Category getCategory(Long id) {
		Optional<Category> category = repository.findById(id);
		if (category.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return category.get();
	}

	public Category createCategoryByController(CategoryDto dto) {
		return repository.save(dto.toCategory());
	}

	public Category updateCategoryByController(Category category) {
		return repository.save(category);
	}

}
