package edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;


public record AccountHolderRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @Pattern(
                regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",
                message = "Invalid phone number format"
        )
        String phone,

        @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotNull(message = "Role is required")
        @Enumerated(EnumType.STRING)
        Role role,

        AddressRequestDTO addressRequestDTO
) { }
