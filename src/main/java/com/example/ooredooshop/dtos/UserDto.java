package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data

@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private Date creationDate;
    private Date lastModifiedDate;

    private  String username;
    private String fullName;
    private Integer phoneNumber;
    private String email;
    private String password;

}
