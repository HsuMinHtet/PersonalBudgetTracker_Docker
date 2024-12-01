package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.UserRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.UserResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.UserMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;
import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.UserRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.service.UserService;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserResponseDTO> createUserAdmin(UserRequestDTO userRequestDTO) {
        User newAdminUser= userMapper.userRequestDTOToUser(userRequestDTO);
        AuditData auditData = AuditDataCreate.populateAuditData(userRequestDTO.name());
        newAdminUser.setAuditData(auditData);
        return Optional.of(userMapper.userToUserResponseDTO(userRepository.save(newAdminUser)));
    }
}
