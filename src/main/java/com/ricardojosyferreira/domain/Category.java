package com.ricardojosyferreira.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String categoryName;
	private Long parentId;
	@CreationTimestamp
	private Instant createAt;

	public Category(Long id) {
		this.id = id;
	}

	public Category(String categoryName, Long parentId) {
		this.categoryName = categoryName;
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", parentId=" + parentId + "]";
	}
	
}
