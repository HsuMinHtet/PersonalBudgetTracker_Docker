package edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "Category Name is required")
         String name,
         String description,
         Integer accountHolder_id
) { }
