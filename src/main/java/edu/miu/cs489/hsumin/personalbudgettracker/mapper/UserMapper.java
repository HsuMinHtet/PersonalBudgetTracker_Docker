package edu.miu.cs489.hsumin.personalbudgettracker.mapper;


import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.UserRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.UserResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestDTOToUser(UserRequestDTO userRequestDTO);
    UserResponseDTO userToUserResponseDTO(User user);
}
