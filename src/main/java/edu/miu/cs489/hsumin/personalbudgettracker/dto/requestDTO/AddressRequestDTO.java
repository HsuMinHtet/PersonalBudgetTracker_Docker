package edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO (
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Street is required")
        String street,
        @NotBlank(message = "Home Number is required")
        String number,
        @NotBlank(message = "PostalCode is required")
        @Size(min = 5,max = 5, message = "Invalid PostalCode, must be 5 digits")
        Integer postalCode,
        @NotBlank(message = "Country is required")
        String country,
        @NotBlank(message = "State is required")
        String state
){ }
