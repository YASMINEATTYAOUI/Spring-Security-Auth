package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Color;

import java.util.List;

public interface ColorService {

    void createColor(Color color);
    Color updateColor(Color color);
    Color getColorById(Long id);

    List<Color> getAllColorsSortedByCreationDate();
    List<Color> getAllColorsByCreatorIdSortedByCreationDate(Long creatorId, String name);
    List<Color> searchColorsByName(String keyword);

    void deleteColorById(Long id);
    void deleteMultipleColorsByIds(List<Long> ids);
    long countColors();
}
