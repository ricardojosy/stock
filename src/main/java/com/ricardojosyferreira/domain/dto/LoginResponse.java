package com.ricardojosyferreira.domain.dto;

public record LoginResponse(String accessToken, String expires, String username) {
}