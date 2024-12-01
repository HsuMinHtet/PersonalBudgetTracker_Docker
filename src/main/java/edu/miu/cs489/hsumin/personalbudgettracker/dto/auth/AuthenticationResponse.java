package edu.miu.cs489.hsumin.personalbudgettracker.dto.auth;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;

public record AuthenticationResponse(
        String token,
        Integer id,
        Role role
) {

}
