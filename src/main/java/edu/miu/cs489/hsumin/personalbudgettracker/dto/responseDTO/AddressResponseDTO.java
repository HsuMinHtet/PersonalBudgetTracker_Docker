package edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO;

public record AddressResponseDTO(
        String city,
        String street,
        String number,
        Integer postalCode,
        String country,
        String state
) { }
