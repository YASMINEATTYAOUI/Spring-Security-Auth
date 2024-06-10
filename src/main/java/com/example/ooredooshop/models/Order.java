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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private Integer numberOrder;
    private Integer articlesNumber;
    private Float totalPrice;
    private String deliveryType;
    private Boolean orderStatus;
    @CreatedDate
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public Boolean isVerified() {
        return orderStatus;
    }

}
