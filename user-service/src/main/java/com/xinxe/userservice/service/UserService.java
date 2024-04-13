package com.xinxe.userservice.service;

import com.xinxe.userservice.dto.UserDto;
import com.xinxe.userservice.jpa.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
  UserDto createUser(UserDto userDto);
  UserDto getUserByUserId(String userId);
  Iterable<Users> getUserByAll();
  UserDto getUserDetailsByEmail(String username);
}
