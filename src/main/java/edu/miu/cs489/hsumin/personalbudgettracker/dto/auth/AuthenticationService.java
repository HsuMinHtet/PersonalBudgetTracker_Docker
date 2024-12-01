package edu.miu.cs489.hsumin.personalbudgettracker.dto.auth;

import edu.miu.cs489.hsumin.personalbudgettracker.config.JWTService;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.AccountDuplicateException;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.UserNotFoundException;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.AccountHolderMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.UserRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountHolderRepository accountHolderRepository;
    private final UserRepository userRepository;
    private final AccountHolderMapper accountHolderMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationResponse accountHolderRegister(AccountHolderRequestDTO accountHolderRequestDTO) {
        //check duplicate user
        Optional<AccountHolder> accountHolderDuplication=accountHolderRepository.findByEmail(accountHolderRequestDTO.email());
        //no duplicate
        if(accountHolderDuplication.isEmpty()){
            AccountHolder accountHolder=accountHolderMapper.accountHolderRequestDTOToAccountHolder(accountHolderRequestDTO);
            accountHolder.setPassword(passwordEncoder.encode(accountHolderRequestDTO.password()));
            AccountHolder registerAccountHolder= accountHolderRepository.save(accountHolder);
            //generate token
            String token= jwtService.generateToken(registerAccountHolder);
            return new AuthenticationResponse(token, registerAccountHolder.getId(), registerAccountHolder.getRole());
        }
        throw new AccountDuplicateException("This email [ "+accountHolderRequestDTO.email()+" ] is already used.");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        //authentication object creation to send to the AuthenticationManager
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
        //After authentication is successfully, generate Token for this authenticated user
        User user= (User) authentication.getPrincipal();

        // Update the last login time
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token, user.getId(), user.getRole());
    }

    public void sendPasswordResetToken(ForgotPasswordRequest forgotPasswordRequest) {
        AccountHolder accountHolder = accountHolderRepository.findByEmail(forgotPasswordRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + forgotPasswordRequest.email()));

        // Generate a random 5-character password
        String defaultPassword = generateRandomPassword(8)+"@123";
        accountHolder.setPassword(passwordEncoder.encode(defaultPassword)); // Token expires in 1 hour
        accountHolderRepository.save(accountHolder);

        // Send email with default password and instructions
        String emailBody = String.format(
                "Hello %s,\n\nYour default password is: %s\n\n" +
                        "Please log in using this password and update your password immediately.\n" +
                        "Thank you!\n\nBest regards,\nPersonal Budget Tracker",
                accountHolder.getEmail(),
                " [ "+ defaultPassword+" ] "
        );

        // Send the email
        emailService.sendEmail(
                accountHolder.getEmail(),
                "Your Default Password",
                emailBody
        );
    }
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

}
