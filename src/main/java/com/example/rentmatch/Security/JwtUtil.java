package com.example.rentmatch.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";

    private final Key SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    //For Generating JWT Token
    public String generateToken(String email,String role){
        return Jwts.builder().
                setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
                .signWith(SECRET_KEY)
                .compact();

    }
   //Extracting the email From token
    public String extractEmail(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    //Extracting the role from token
    public String extractRole(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    //Validating the token
    public boolean validateToken(String token,String email){
        String extractedEmail=extractEmail(token);

        return extractedEmail.equals(email) && !isTokenExpired(token);

    }

    //Checking the token expiration
    public boolean isTokenExpired(String token){

        Claims claims=Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());

    }


}
