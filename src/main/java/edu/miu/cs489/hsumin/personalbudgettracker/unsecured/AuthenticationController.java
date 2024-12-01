package edu.miu.cs489.hsumin.personalbudgettracker.unsecured;


import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationRequest;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationResponse;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.AuthenticationService;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.auth.ForgotPasswordRequest;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //AccountHolderRegister
    @PostMapping("/account-holder-register")
    public ResponseEntity<AuthenticationResponse> accountHolderRegister(@RequestBody AccountHolderRequestDTO accountHolderRequestDTO){
        AuthenticationResponse authenticationResponse=authenticationService.accountHolderRegister(accountHolderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    //Login
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse=authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authenticationService.sendPasswordResetToken(request);
        return ResponseEntity.ok("Password reset link sent to your email.");
    }


}
