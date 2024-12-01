package edu.miu.cs489.hsumin.personalbudgettracker.service;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.UserRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.UserResponseDTO;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDTO> createUserAdmin(UserRequestDTO userRequestDTO);
}
