package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Color;
import com.example.ooredooshop.repositories.ColorRepository;
import com.example.ooredooshop.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private static final Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);

    @Override
    public void createColor(Color color) {
        color.setCreationDate(new Date());
        color.setLastModifiedDate(new Date());
        colorRepository.save(color);
        logger.info("Color {} is saved", color.getId());
    }

    @Override
    public Color updateColor(Color updatedColor) {

        Color finalUpdatedColor = updatedColor;
        Color existingColor = colorRepository.findById(updatedColor.getId())
                .orElseThrow(() -> new NotFoundException("Color with ID " + finalUpdatedColor.getId() + " not found"));

        updatedColor = colorRepository.save(existingColor);
        logger.info("Color {} got updated", updatedColor.getId());
        updatedColor.setLastModifiedDate(new Date());
        return updatedColor;
    }

    @Transactional
    public Color updateColor(Long roleId, Color updatedColor) {
        // Fetch the existing role
        Color existingColor = colorRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        // Update the role's fields
        existingColor.setName(updatedColor.getName());
        existingColor.setColorCode(updatedColor.getColorCode());
        existingColor.setLastModifiedDate(new Date());

        // Save the updated role
        return colorRepository.save(existingColor);
    }

    @Override
    public Color getColorById(Long id) {

        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Color with ID " + id + " not found"));
        logger.info("Color {} is fetched", color.getId());

        return null;
    }

    @Override
    public List<Color> getAllColorsSortedByCreationDate() {

        logger.info("Retrieving All Colors (Sorted)");
        return colorRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Color> searchColorsByName(String keyword ) {
        return colorRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword );
    }

    @Override
    public void deleteColorById(Long id) {

        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Color not found with ID: " + id));
        colorRepository.delete(color);
        logger.info("Color {} is deleted", color.getId());

    }

    @Override
    public void deleteMultipleColorsByIds(List<Long> ids) {

        logger.info("Batch deletion of colors with IDs: {}", ids);
        colorRepository.deleteAllById(ids);
    }

    @Override
    public long countColors() {
        return colorRepository.count();
    }

}
