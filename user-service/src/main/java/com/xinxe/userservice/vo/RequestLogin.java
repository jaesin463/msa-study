package com.xinxe.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

  @Email
  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email not be less than two characters")
  private String email;
  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be equal or greater than eight characters")
  private String password;
}
