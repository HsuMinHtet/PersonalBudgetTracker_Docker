package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.UserRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.UserResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T16:11:50-0600",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userRequestDTOToUser(UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRequestDTO.name() );
        user.setPhone( userRequestDTO.phone() );
        user.setPassword( userRequestDTO.password() );
        user.setEmail( userRequestDTO.email() );
        user.setRole( userRequestDTO.role() );

        return user;
    }

    @Override
    public UserResponseDTO userToUserResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        Role role = null;

        name = user.getName();
        role = user.getRole();

        UserResponseDTO userResponseDTO = new UserResponseDTO( name, role );

        return userResponseDTO;
    }
}
