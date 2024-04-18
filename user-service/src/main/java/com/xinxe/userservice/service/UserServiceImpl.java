package com.xinxe.userservice.service;

import com.xinxe.userservice.client.OrderServiceClient;
import com.xinxe.userservice.config.Resilience4JConfig;
import com.xinxe.userservice.jpa.Users;
import com.xinxe.userservice.dto.UserDto;
import com.xinxe.userservice.jpa.UserRepository;
import com.xinxe.userservice.vo.ResponseOrder;
import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  //  private final RestTemplate restTemplate;
  private final Environment env;
  private final UserRepository userRepository;
  private final OrderServiceClient orderServiceClient;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CircuitBreakerFactory circuitBreakerFactory;

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

//    List<ResponseOrder> orders = new ArrayList<>();
    /* Using as rest template */
//    String orderUrl = String.format(env.getProperty("order-service.url"), userId);
//    ResponseEntity<List<ResponseOrder>> orderListResponse =
//        restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//            new ParameterizedTypeReference<List<ResponseOrder>>() {
//            });
//    List<ResponseOrder> orderList = orderListResponse.getBody();

    /* Using as feignclient */
    /* Feign exception handling */
//    List<ResponseOrder> orderList = null;
//    try {
//      orderList = orderServiceClient.getOrders(userId);
//    } catch (FeignException ex){
//      log.error(ex.getMessage());
//    }

    /* ErrorDecoder */
//    List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
    log.info("Before call orders microservice");
    CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
    List<ResponseOrder> orderList = circuitbreaker.run(() -> orderServiceClient.getOrders(userId),
        throwable -> new ArrayList<>());
    log.info("After call orders microservice");

    userDto.setOrders(orderList);

    return userDto;
  }

  @Override
  public Iterable<Users> getUserByAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    Users users = userRepository.findByEmail(email);

    if (users == null) {
      throw new UsernameNotFoundException(email);
    }

    UserDto userDto = new ModelMapper().map(users, UserDto.class);
    return userDto;
  }
}
