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
    private  String username;
    private String fullName;
    private String email;
    private Integer phoneNumber;
    private String password;

    private Date creationDate;
    private Date lastModifiedDate;

}
