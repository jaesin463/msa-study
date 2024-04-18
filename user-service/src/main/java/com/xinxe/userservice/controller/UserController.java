package com.xinxe.userservice.controller;

import com.xinxe.userservice.dto.UserDto;
import com.xinxe.userservice.jpa.Users;
import com.xinxe.userservice.service.UserServiceImpl;
import com.xinxe.userservice.vo.Greeting;
import com.xinxe.userservice.vo.RequestLogin;
import com.xinxe.userservice.vo.RequestUser;
import com.xinxe.userservice.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

  private Environment env;
  private Greeting greeting;

  private UserServiceImpl userService;

  public UserController (Environment env, Greeting greeting, UserServiceImpl userService){
    this.env = env;
    this.greeting = greeting;
    this.userService = userService;
  }

  @GetMapping("/health_check")
  @Timed(value = "users.status", longTask = true)
  public String status(){
    return String.format("It's Working in User Service"
        + ", port(local.server.port)=" + env.getProperty("local.server.port")
        + ", port(server.port)=" + env.getProperty("server.port")
        + ", token secret=" + env.getProperty("token.secret")
        + ", token expiration time=" + env.getProperty("token.expiration.time"));
  }

  @GetMapping("/welcome")
  @Timed(value = "users.welcome", longTask = true)
  public String welcome(){
//    return env.getProperty("greeting.message");
    return greeting.getMessage();
  }

  @PostMapping("/users")
  public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = mapper.map(user, UserDto.class);
    userService.createUser(userDto);

    ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
  }

  @GetMapping("/users")
  public ResponseEntity<List<ResponseUser>> getUsers() {
    Iterable<Users> userList = userService.getUserByAll();

    List<ResponseUser> result = new ArrayList<>();
    userList.forEach(v -> {
      result.add(new ModelMapper().map(v, ResponseUser.class));
    });

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ResponseUser> getUsers(@PathVariable String userId) {
    UserDto userDto = userService.getUserByUserId(userId);

    ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

    return ResponseEntity.status(HttpStatus.OK).body(returnValue);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin){
    System.out.println("야 이놈아");
    return null;
  }
}
