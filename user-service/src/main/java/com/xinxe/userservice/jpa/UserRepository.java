package com.xinxe.userservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

  Users findByUserId(String userId);

  Users findByEmail(String email);
}
