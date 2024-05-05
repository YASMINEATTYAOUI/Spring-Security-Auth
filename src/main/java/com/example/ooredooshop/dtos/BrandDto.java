package com.example.ooredooshop.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    private Long id;
    private String name;
    private Long pictureId;
    private String creationDate;
    private Long creatorId;
    private String lastModifiedDate;
    private String lastModifierId;
}
