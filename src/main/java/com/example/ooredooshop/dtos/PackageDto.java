package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {
    private Long id;
    private String reference;
    private String description;
    private Integer nbProduct;
    private Long pictureId;
    private String tags;
    private Float price;
    private Date creationDate;
    private Long creatorId;
    private Date lastModifiedDate;
    private Long lastModifierId;
}
