package edu.miu.cs489.hsumin.personalbudgettracker.unsecured;

import edu.miu.cs489.hsumin.personalbudgettracker.config.JWTService;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationRequest;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationResponse;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationService;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.ForgotPasswordRequest;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    void accountHolderRegister() {
        // Arrange
        AccountHolderRequestDTO requestDTO = new AccountHolderRequestDTO(
                "TestName",
                "1234567898",
                "password123",
                "test@example.com",
                Role.ACCOUNT_HOLDER,
                null
        );

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1);
        accountHolder.setName("TestName");
        accountHolder.setPhone("1234567898");
        accountHolder.setPassword("encodedPassword123");
        accountHolder.setEmail("test@example.com");
        accountHolder.setRole(Role.ACCOUNT_HOLDER);

        AuthenticationResponse response = new AuthenticationResponse("mockToken", 1, Role.ACCOUNT_HOLDER);

        // Mock behavior
        Mockito.when(authenticationService.accountHolderRegister(requestDTO)).thenReturn(response);

        // Act
        ResponseEntity<AuthenticationResponse> result = authenticationController.accountHolderRegister(requestDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(response.token(), result.getBody().token());
        Assertions.assertEquals(response.id(), result.getBody().id());
        Assertions.assertEquals(response.role(), result.getBody().role());

        Mockito.verify(authenticationService).accountHolderRegister(requestDTO);
    }

    @Test
    void authenticate() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com","password123");;

        AuthenticationResponse response = new AuthenticationResponse("mockToken",1,Role.ACCOUNT_HOLDER);

        Mockito.when(authenticationService.authenticate(authRequest)).thenReturn(response);

        // Act
        ResponseEntity<AuthenticationResponse> result = authenticationController.authenticate(authRequest);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(response.token(), result.getBody().token());
        Assertions.assertEquals(response.id(), result.getBody().id());
        Assertions.assertEquals(response.role(), result.getBody().role());
        Mockito.verify(authenticationService).authenticate(authRequest);
    }

    @Test
    void forgotPassword() {
        // Arrange
        ForgotPasswordRequest request = new ForgotPasswordRequest("test@example.com");

        // Act
        ResponseEntity<String> result = authenticationController.forgotPassword(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("Password reset link sent to your email.", result.getBody());
        Mockito.verify(authenticationService).sendPasswordResetToken(request);
    }
}
