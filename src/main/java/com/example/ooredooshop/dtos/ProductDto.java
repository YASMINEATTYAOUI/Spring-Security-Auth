package com.example.ooredooshop.dtos;

import com.example.ooredooshop.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String reference;
    private String description;
    private String image;
    private Float price;
    private Integer soldQuantity;
    private Integer availableQuantity;
    private Date creationDate;
    private Date lastModifiedDate;
    private Category Category;
}
