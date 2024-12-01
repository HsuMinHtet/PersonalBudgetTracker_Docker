package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.UserRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.UserResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.UserMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.UserRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void createUserAdmin_validInput_returnAdmin() {
        // Prepare
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "Admin User",
                "(123)-456-7890",
                "admin@example.com123",
                "AdminPassword",
                Role.ADMIN
        );
        User user = new User();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setRole(userRequestDTO.role());

        AuditData auditData = AuditDataCreate.populateAuditData(userRequestDTO.name());
        user.setAuditData(auditData);

        UserResponseDTO responseDTO = new UserResponseDTO(
                "Admin User",
                Role.ADMIN
        );

        Mockito.when(userMapper.userRequestDTOToUser(userRequestDTO)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.userToUserResponseDTO(user)).thenReturn(responseDTO);

        // Act
        Optional<UserResponseDTO> result = userService.createUserAdmin(userRequestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(userMapper).userRequestDTOToUser(userRequestDTO);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userMapper).userToUserResponseDTO(user);
    }
}
