package com.example.ooredooshop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private Long creatorId;
    @LastModifiedDate
    private Date lastModifiedDate;
    @LastModifiedBy
    private String lastModifierId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Category> caterogies = new HashSet<>();

}
