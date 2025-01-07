package com.server.DentalCenterLlanos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.LlanosDentalCare.LlanosDentalCareBackend.Model.Usuarios_RolesModel;

public class JwtUtil {

    private static final String SECRET_KEY = "tumkan_R_96";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas en milisegundos

    // Generar el token
    public static String generateToken(String username, List<com.server.DentalCenterLlanos.Model.Usuarios_RolesModel> roles) {
        List<String> roleNames = roles.stream()
                                      .map(role -> role.getRol().getNombre())
                                      .collect(Collectors.toList());

        return Jwts.builder()
                   .setSubject(username)
                   .claim("roles", roleNames)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                   .compact();
    }


    // Validar el token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener el usuario del token
    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public static List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
                
        return claims.get("roles", List.class);
    }
}
