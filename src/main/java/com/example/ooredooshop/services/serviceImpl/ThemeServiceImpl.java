package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Theme;
import com.example.ooredooshop.repositories.ThemeRepository;
import com.example.ooredooshop.services.ThemeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private static final Logger logger = LoggerFactory.getLogger(ThemeServiceImpl.class);
    @Override
    public void createTheme(Theme theme) {
        themeRepository.save(theme);
        logger.info("Theme {} is saved", theme.getId());
    }
    @Override
    public Theme updateTheme(Theme updatedTheme) {

        Theme finalUpdatedTheme = updatedTheme;
        Theme existingTheme = themeRepository.findById(updatedTheme.getId())
         .orElseThrow(() -> new NotFoundException("Theme with ID " + finalUpdatedTheme.getId() + " not found"));

         updatedTheme = themeRepository.save(existingTheme);
         logger.info("Theme {} got updated", updatedTheme.getId());

        return updatedTheme;
    }

    @Override
    public Theme getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Theme with ID " + id + " not found"));
        logger.info("Theme {} is fetched", theme.getId());

        return theme;
    }
    @Override
    public List<Theme> getAllThemesSortedByCreationDate() {
        logger.info("Retrieving All Themes (Sorted)");
        return themeRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Theme> searchThemesByName(String keyword) {
        return themeRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }
    @Override
    public void deleteThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Theme not found with ID: " + id));
        themeRepository.delete(theme);
        logger.info("Theme {} is deleted", theme.getId());
    }

    @Override
    public void deleteMultipleThemesByIds(List<Long> ids) {
        logger.info("Batch deletion of themes with IDs: {}", ids);
        themeRepository.deleteAllById(ids);
    }
    @Override
    public long countThemes() {
        return themeRepository.count();
    }
}