package com.example.demo.dto.login;



public class RegisterRequest {
    
    String username;
    String password;
    String firstname;
    String lastname;

    //getters setters y constructor
  
   public String getUsername(){
    return username;
   }
   public void setUsername(String username){
    this.username = username;
   }

    public String getPassword(){
    return password;
   }
   public void setPassword(String password){
    this.password = password;
   }
   
      public String getFirstname(){
    return firstname;
   }
   public void setFirstname(String firstname){
    this.firstname = firstname;
   }

    public String getLastname(){
    return lastname;
   }
   public void setLastname(String lastname){
    this.lastname = lastname;
   }
  
   
   
    
    
}
