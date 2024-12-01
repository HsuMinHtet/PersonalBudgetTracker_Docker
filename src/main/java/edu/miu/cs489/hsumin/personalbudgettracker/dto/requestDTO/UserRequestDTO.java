package edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
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
        String password,

        @NotBlank(message = "Email is required")
        @Email
        String email,
        Role role
) { }
