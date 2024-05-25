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
import java.util.HashSet;
import java.util.Set;

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
    @LastModifiedDate
    private Date lastModifiedDate;

    public Boolean isActive() {
        return active;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Privilege> privileges = new HashSet<>();

}
