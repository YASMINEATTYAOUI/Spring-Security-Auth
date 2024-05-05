package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Date creationDate;
    private Long creatorId;
    private Date lastModifiedDate;
    private String lastModifierId;
}
