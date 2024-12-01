package edu.miu.cs489.hsumin.personalbudgettracker.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {

      @Value("${jjt.secretary}")
    public String SECRET;

      public String generateToken(UserDetails userDetails){
        String token= Jwts.builder()
                 .issuedAt(new Date())
                 .issuer("BudgetTracker") //issuer
                 .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                 .subject(userDetails.getUsername()) //identity (user name)
                 .claim(
                         "authorities", userDetails.getAuthorities()) //include in payload
                 .signWith(signInKey())
                 .compact();
         return token;
      }

    private SecretKey signInKey() {
          //hash based message authentication code
          return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public Claims getClaims(String token){
       return Jwts.parser()
               .verifyWith(signInKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();

    }

    public String populateAuthorities(UserDetails userDetails){
        userDetails.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

        return userDetails.getAuthorities()
                  .stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.joining(","));
      }
}

