package com.ricardojosyferreira.repositories;

import com.ricardojosyferreira.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Optional<Product> findById(Long id);

	Product save(Product product);

	void deleteById(Long id);
}
