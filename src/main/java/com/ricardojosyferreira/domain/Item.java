package com.ricardojosyferreira.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ricardojosyferreira.domain.dto.ItemDto;
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
@Table(name="items")
public class Item {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal price;
	private Integer quantity;
	private BigDecimal total;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	@CreationTimestamp
	private Instant createAt;

	public Item(ItemDto dto) {
		this.price = dto.price();
		this.quantity = dto.quantity();
		this.total = dto.total();
	}
}
