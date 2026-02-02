package com.geprice.controller;

import com.geprice.Constants;
import com.geprice.Util;
import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.Category;
import com.geprice.repository.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable String id) {
        int categoryId = Util.validateIntegerParameter(id, Constants.CATEGORY_NOT_FOUND);

        Optional<Category> category = categoryRepo.findById(categoryId);
        if(category.isPresent()) {
            log.debug("Category found: {}", category.get().getName());
            return category.get();
        } else {
            log.warn("Category not found: {}", id);
            throw new GEPrice404Error(Constants.CATEGORY_NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<Category> getAll()
    {
        return categoryRepo.findAll();
    }
}
