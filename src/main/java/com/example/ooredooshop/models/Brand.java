package com.example.ooredooshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long pictureId;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private Long creatorId;
    @LastModifiedDate
    private Date lastModifiedDate;
    @LastModifiedBy
    private String lastModifierId;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> caterogies = new HashSet<>();

}
