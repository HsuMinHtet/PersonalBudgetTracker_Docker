package edu.miu.cs489.hsumin.personalbudgettracker.config;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.RequestContextFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private  final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtfilter;

    //White List
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestContextFilter requestContextFilter) throws Exception {
         http.
                csrf(CsrfConfigurer::disable)
                 .cors(cors -> cors.configurationSource(request -> {
                     var config = new org.springframework.web.cors.CorsConfiguration();
                     config.setAllowedOrigins(List.of("http://localhost:5173")); // Set allowed origins
                     config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Set allowed HTTP methods
                     config.setAllowedHeaders(List.of("accountholder_id","Authorization", "Content-Type", "Accept")); // Set allowed headers
                     config.setAllowCredentials(true); // Allow credentials (cookies, Authorization header)
                     return config;
                 }))
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers("/api/v1/auth/*").permitAll()
                                        //.requestMatchers("/api/v1/auth/account-holder-register").permitAll()
                                       // .requestMatchers("/api/v1/auth/forgot-password").permitAll()
                                        //.requestMatchers("/api/v1/account-holder/").permitAll()
                                        .anyRequest()
                                        .authenticated()
                ).authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
