package com.ricardojosyferreira.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;
	private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
	private Category category;
	private BigDecimal price;
	private Boolean available;
	private Integer quantity;
	@CreationTimestamp
	private Instant createAt;

	public Product(String productName, String description, Category category, BigDecimal price, Boolean available, Integer quantity) {
		this.productName = productName;
		this.description = description;
		this.category = category;
		this.price = price;
		this.available = available;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", description='" + description + '\'' +
				", category=" + category +
				", price=" + price +
				", available=" + available +
				", quantity=" + quantity +
				", createAt=" + createAt +
				'}';
	}
}
