package com.xinxe.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinxe.userservice.dto.UserDto;
import com.xinxe.userservice.service.UserService;
import com.xinxe.userservice.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private UserService userService;
  private Environment env;

  public AuthenticationFilter(AuthenticationManager authenticationManager,
      UserService userService, Environment env) {
    super(authenticationManager);
    this.userService = userService;
    this.env = env;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(),
          RequestLogin.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getEmail(),
              creds.getPassword(),
              new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication auth) throws IOException, ServletException {
    String userName = ((User) auth.getPrincipal()).getUsername();
    UserDto userDetails = userService.getUserDetailsByEmail(userName);

    byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());

    SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

    Instant now = Instant.now();

    String token = Jwts.builder()
        .subject(userDetails.getUserId())
        .expiration(
            Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
        .issuedAt(Date.from(now))
        .signWith(secretKey)
        .compact();

    res.addHeader("token", token);
    res.addHeader("userId", userDetails.getUserId());
  }
}
