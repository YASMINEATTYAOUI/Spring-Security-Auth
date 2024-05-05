package com.example.ooredooshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private Long id;
    private String name;
    private String description;
    private String creationDate;
    private Long creatorId;
    private String lastModifiedDate;
    private String lastModifierId;
}
