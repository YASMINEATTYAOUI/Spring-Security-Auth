package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Color;
import com.example.ooredooshop.repositories.ColorRepository;
import com.example.ooredooshop.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private static final Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);

    @Override
    public void createColor(Color color) {

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

        return updatedColor;
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
    public List<Color>  getAllColorsByCreatorIdSortedByCreationDate(Long creatorId, String name ) {
        if(name != null){
            return colorRepository.findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(creatorId, name);
        }
        return colorRepository.findByCreatorIdOrderByCreationDate(creatorId);
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
