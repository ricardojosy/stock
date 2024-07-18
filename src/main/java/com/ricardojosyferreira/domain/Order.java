package com.ricardojosyferreira.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal total;
	@CreationTimestamp
	private Instant createAt;

	@OneToMany(mappedBy="order")
	private List<Item> items = new ArrayList<>();

	@Override
	public String toString() {
		return "Order [id=" + id + ", total=" + total + ", createAt=" + createAt + "]";
	}
	
}
