package com.example.ooredooshop.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numberOrder;
    private Integer articlesNumber;
    private Float totalPrice;
    private String orderStatus;
    private String deliveryType;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private Long creatorId;
}
