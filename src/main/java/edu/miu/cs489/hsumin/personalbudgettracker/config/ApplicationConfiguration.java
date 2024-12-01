package edu.miu.cs489.hsumin.personalbudgettracker.config;

import edu.miu.cs489.hsumin.personalbudgettracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository){
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return
                email -> userRepository.findByEmail(email)
                        .orElseThrow(()-> new UsernameNotFoundException(email)
                        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
