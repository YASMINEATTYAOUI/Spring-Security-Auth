package com.example.ooredooshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reference;
    private String description;
    private String tags;
    private Float price;
    private String pictureId;
    private Integer soldQuantity;
    private Integer availableQuantity;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private Long creatorId;
    @LastModifiedDate
    private Date lastModifiedDate;
    @LastModifiedBy
    private  Long lastModifierId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id")
    private Category category;

}
