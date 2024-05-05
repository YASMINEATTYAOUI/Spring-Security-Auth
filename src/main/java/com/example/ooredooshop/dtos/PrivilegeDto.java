package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeDto {

    private Long id;
    private String description;
    private Date creationDate;
    private Long creatorId;
    private Date lastModifiedDate;
    private String lastModifierId;
}
