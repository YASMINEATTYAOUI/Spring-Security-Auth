package com.example.ooredooshop.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Integer phoneNumber;
    private String adress;
    private Boolean status;
    @CreatedDate
    private Date creationDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    public boolean isActive() {
        return status;
    }
}
