package com.xinxe.userservice.service;

import com.xinxe.userservice.jpa.Users;
import com.xinxe.userservice.dto.UserDto;
import com.xinxe.userservice.jpa.UserRepository;
import com.xinxe.userservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users = userRepository.findByEmail(username);

    if (users == null) {
      throw new UsernameNotFoundException(username);
    }

    return new User(users.getEmail(), users.getEncryptedPwd(),
        true, true, true, true, new ArrayList<>());
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    Users users = mapper.map(userDto, Users.class);
    users.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

    userRepository.save(users);

    UserDto returnUserDto = mapper.map(users, UserDto.class);

    return returnUserDto;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    Users users = userRepository.findByUserId(userId);

    if (users == null) {
      throw new UsernameNotFoundException("User not found");
    }

    UserDto userDto = new ModelMapper().map(users, UserDto.class);

    List<ResponseOrder> orders = new ArrayList<>();
    userDto.setOrders(orders);

    return userDto;
  }

  @Override
  public Iterable<Users> getUserByAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    Users users = userRepository.findByEmail(email);

    if(users == null)
      throw new UsernameNotFoundException(email);

    UserDto userDto = new ModelMapper().map(users, UserDto.class);
    return userDto;
  }
}
