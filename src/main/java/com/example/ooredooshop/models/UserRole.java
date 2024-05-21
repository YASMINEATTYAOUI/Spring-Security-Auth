package com.example.ooredooshop.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    private String name;
    private String description;
    private Boolean active;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private Long creatorId;
    @LastModifiedDate
    private Date lastModifiedDate;
    @LastModifiedBy
    private String lastModifierId;

    public Boolean isActive() {
        return active;
    }


}
