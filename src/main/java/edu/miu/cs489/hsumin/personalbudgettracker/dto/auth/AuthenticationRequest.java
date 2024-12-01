package edu.miu.cs489.hsumin.personalbudgettracker.dto.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}
