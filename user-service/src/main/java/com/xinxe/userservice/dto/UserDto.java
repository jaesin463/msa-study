package com.xinxe.userservice.dto;

import com.xinxe.userservice.vo.ResponseOrder;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserDto {
  private String email;
  private String name;
  private String pwd;
  private String userId;
  private Date createdAt;

  private String encryptedPwd;

  private List<ResponseOrder> orders;
}