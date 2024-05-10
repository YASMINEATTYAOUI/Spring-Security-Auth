package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Integer numberOrder;
    private Integer articlesNumber;
    private Float totalPrice;
    private String orderStatus;
    private String deliveryType;
    private Date creationDate;
    private Long creatorId;
}
