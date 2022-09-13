package com.garcia.giovane.form.contract.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserContractResponse(
        String name,
        LocalDate birthDate,
        Long id,
        String imageName
) {
}
