package com.example.demo.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { //ESTA CLASE SE USA PARA CREAR FILTROS PERSONALIZADOS


    private final JwtService jwtService;
    private final JwtUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) //este metodo va a realizar todos los filtros relacionados con el token
            throws ServletException, IOException {
       //primer paso: obtener el token del request

       //creamos la variable 
       final String token = getTokenFromRequest(request);
       final String username;

       if (token == null ) {
       
        //si el token es nulo, no hacemos nada, simplemente dejamos pasar la solicitud
        filterChain.doFilter(request, response);
        return;
       }

       username = jwtService.getUsernameFromToken(token); //obtenemos el username del token
       
       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { //si el username no es nulo y el token es valido
        UserDetails userDetails = userDetailsService.loadUserByUsername(username); //obtenemos los detalles del usuario a partir del username
    
       if (jwtService.validateToken(token, userDetails)) { //si el token es valido
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //creamos un token de autenticacion a partir de los detalles del usuario
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //le agregamos los detalles de la solicitud al token de autenticacion
            SecurityContextHolder.getContext().setAuthentication(authToken); //establecemos el token de autenticacion en el contexto de seguridad
        }
       }

       //si el token no es valido, simplemente dejamos pasar la solicitud, ya que spring security se encargara de bloquearla si es necesario
    
    

       filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) { //ESTE METODO DEVUELVE EL TOKEN
        
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); //obtenemos el header de autorizacion
   
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { //verificamos que el header no sea nulo y que comience con "Bearer "
            System.out.println("Token: " + authHeader.substring(7)); //imprimimos el token sin el prefijo "Bearer "
            return authHeader.substring(7); //si es asi, devolvemos el token sin el prefijo "Bearer "
        } else {
            System.out.println("Token no encontrado o no valido"); //si no es asi, imprimimos un mensaje de error
        }
        
        return null; //si no es asi, devolvemos null
    }

    
}
