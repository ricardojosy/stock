package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Category;
import com.ricardojosyferreira.domain.Product;

public record CategoryDto(
        String name,
        Long parentId) {

    public Category toCategory() {
        return new Category(name,parentId);
    }

    public Category toCategory(Category category) {
        category.setCategoryName(name);
        category.setParentId(parentId);
        return category;
    }
}
