package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Theme;

import java.util.List;

public interface ThemeService {
    void createTheme(Theme themeRequest);
    Theme updateTheme(Theme theme);
    Theme getThemeById(Long id);
    List<Theme> getAllThemesSortedByCreationDate( );
    List<Theme> getAllThemesByCreatorIdSortedByCreationDate(Long creatorId, String name);
    List<Theme> searchThemesByName(String keyword);

    void deleteThemeById(Long id);
    void deleteMultipleThemesByIds(List<Long> ids);

    long countThemes();
}
