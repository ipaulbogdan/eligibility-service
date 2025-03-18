package com.idorasi.eligibility.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshToken(@NotBlank String refreshToken) {
}
