package com.xinxe.userservice.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false, length = 50, unique = true)
  private String email;
  @Column(nullable = false, length = 50)
  private String name;
  @Column(nullable = false, unique = true)
  private String userId;
  @Column(nullable = false, unique = true)
  private String encryptedPwd;

}
