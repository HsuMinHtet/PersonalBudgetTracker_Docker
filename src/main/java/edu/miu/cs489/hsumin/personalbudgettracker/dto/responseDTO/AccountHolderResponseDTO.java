package edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;

public record AccountHolderResponseDTO(
        Integer id,
        String name,
        Role role,
        String email,
        String phone,
        AddressResponseDTO addressResponseDTO)
{ }
