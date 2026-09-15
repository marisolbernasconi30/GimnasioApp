package com.example.demo.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder //genera automaticamente AuthResponse.builder().token("...").build()

public class AuthResponse {
      
       String token;

}
