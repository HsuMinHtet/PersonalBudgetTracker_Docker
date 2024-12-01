package edu.miu.cs489.hsumin.personalbudgettracker.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader=request.getHeader("Authorization");
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){// Bearer whitespace
            //Extract the token
            String token= authorizationHeader.substring(7); //count after whitespace
            //Extract username from token
            Claims claims= jwtService.getClaims(token);
            String email= claims.getSubject();
            if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                userDetailsService.loadUserByUsername(email).getAuthorities())
                );
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("api/v1/auth");
    }
}
