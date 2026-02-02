package com.geprice.controller;

import com.geprice.Util;
import com.geprice.pojo.Category;
import com.geprice.pojo.GEPriceError;
import com.geprice.repository.CategoryRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryRepo categoryRepo;

    public CategoryController(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getCategory(@PathVariable String id, HttpServletResponse response) {
        int categoryId;
        try {
            categoryId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            log.warn("Invalid category id format: {}", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Category not found").build());
        }

        Optional<Category> category = categoryRepo.findById(categoryId);
        if(category.isPresent()) {
            log.debug("Category found: {}", category.get().getName());
            return Util.toJson(category.get());
        } else {
            log.warn("Category not found: {}", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Category not found").build());
        }
    }

    @GetMapping("/all")
    public List<Category> getAll()
    {
        return categoryRepo.findAll();
    }
}
