package com.example.demo.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

//import java.security.Key;
import io.jsonwebtoken.security.Keys;
//import com.example.primerJwt.controller.AuthService;



@Service


public class JwtService {

    
private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A7134"; //esta clave se recomienda que sea más segura y se guarde en un lugar seguro, como una variable de entorno

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user); //hashmap se utiliza para guardar pares de claves - valor 
        //lo vamos a usar para pasar info adicional en el token

    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
       return Jwts
            .builder()
            .claims(extraClaims) // Antes setClaims
            .subject(user.getUsername()) // Antes setSubject
            .issuedAt(new Date(System.currentTimeMillis())) // Antes setIssuedAt
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Antes setExpiration
            .signWith(getKey()) // Ya no se pasa el algoritmo aquí si la llave ya lo define
            .compact(); //la firma del token, se recomienda usar una clave secreta más segura
            
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //esto es para decodificar la clave secreta que está en base64, se recomienda usar una clave secreta más segura
        return Keys.hmacShaKeyFor(keyBytes); //esto es para generar la clave a partir de la cadena secreta, se recomienda usar una clave secreta más segura
    }

    public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject); //esto es para obtener el username del token, ya que el username se guarda en el subject del token
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); //esto es para validar el token, se recomienda usar una clave secreta más segura
    }

  private Claims getAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getKey()) // antes era setSigningKey
            .build()
            .parseSignedClaims(token)
            .getPayload();
}
    public <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }


}
