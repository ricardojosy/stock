package com.ricardojosyferreira.domain.dto;

import com.ricardojosyferreira.domain.Order;

import java.util.List;

public record TreeResponseDto(OrderResponseDto data, List<ItemChildDto> children) {
}
