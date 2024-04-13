package com.xinxe.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class RequestUser {

  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email not be less than tho character")
  @Email
  private String email;

  @NotNull(message = "Name cannot be null")
  @Size(min = 2, message = "Name not be less than tho character")
  private String name;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be equal or grater than eight character")
  private String pwd;
}
