package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String reference;
    private String description;
    private String tags;
    private Float price;
    private String pictureId;
    private Integer soldQuantity;
    private Integer availableQuantity;
    private Date creationDate;
    private Long creatorId;
    private Date lastModifiedDate;
    private  Long lastModifierId;
}
