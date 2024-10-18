package com.MyOrganization.MyOrganization.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginDTO {
    @NotNull
     private String email;
    @NotNull
     private String password;
  
}
