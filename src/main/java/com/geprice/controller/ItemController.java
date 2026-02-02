package com.geprice.controller;

import com.geprice.Util;
import com.geprice.pojo.*;
import com.geprice.repository.BossItemRepo;
import com.geprice.repository.BossRepo;
import com.geprice.repository.CategoryItemRepo;
import com.geprice.repository.CategoryRepo;
import com.geprice.repository.ItemRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    private final ItemRepo itemRepo;
    private final BossRepo bossRepo;
    private final BossItemRepo bossItemRepo;
    private final CategoryRepo categoryRepo;
    private final CategoryItemRepo categoryItemRepo;

    ItemController(ItemRepo itemRepo, BossRepo bossRepo, BossItemRepo bossItemRepo, CategoryRepo categoryRepo, CategoryItemRepo categoryItemRepo) {
        this.itemRepo = itemRepo;
        this.bossRepo = bossRepo;
        this.bossItemRepo = bossItemRepo;
        this.categoryRepo = categoryRepo;
        this.categoryItemRepo = categoryItemRepo;
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        return itemRepo.findAll().stream()
                .<Map<String, Object>>map(item -> Map.of("id", item.getId(), "name", item.getName()))
                .toList();
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getItem(@PathVariable String id, HttpServletResponse response) {
        Optional<Item> item = itemRepo.findById(Integer.parseInt(id));
        if(item.isPresent()) {
            log.debug("Item {} with id {} found", item.get().getName(), id);
            return Util.toJson(item.get());
        } else {
            log.warn("Item with id {} not found", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Item not found").build());
        }
    }

    @GetMapping(value = "/search/{query}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ItemsPaged getItems(@PathVariable String query,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") String pageNumber) {
       List<Item> items = itemRepo.findAllByNameUpperContaining(query.toUpperCase(Locale.UK), PageRequest.of(Integer.parseInt(pageNumber),
                                                                                                        Integer.parseInt(pageSize),
                                                                                                        Sort.by("name").ascending()));
       return ItemsPaged.builder()
               .items(items)
               .query(query)
               .pageNumber(Integer.parseInt(pageNumber))
               .pageSize(Integer.parseInt(pageSize))
               .build();
    }

    @GetMapping(value = "/boss/{bossId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getBoss(@PathVariable String bossId, HttpServletResponse response) {
        Optional<Boss> boss = bossRepo.findById(Integer.parseInt(bossId));
        if(boss.isEmpty()) {
            log.error("Boss {} not found", bossId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Boss not found").build());
        }
        BossItems.BossItemsBuilder bossItem = BossItems.builder();
        bossItem.boss(boss.get());

        List<BossItem> bossDropsRaw = bossItemRepo.findAllByBossId(Integer.parseInt(bossId));
        if(bossDropsRaw.isEmpty()) {
            log.warn("No items found for boss {}", bossId);
        }

        List<Item> bossDropItems = new ArrayList<>();
        for(BossItem bossDrop : bossDropsRaw) {
            Optional<Item> item = itemRepo.findById(bossDrop.getItemId());
            if(item.isPresent()) {
                log.debug("Item {} with id {} found for boss {}", item.get().getName(), bossDrop.getItemId(), bossDrop.getBossId());
                bossDropItems.add(item.get());
            } else {
                log.warn("Item with id {} not found for boss {}", bossDrop.getItemId(), bossDrop.getBossId());
            }
        }

        bossItem.items(bossDropItems);
        return Util.toJson(bossItem.build());
    }

    @GetMapping(value = "/category/{categoryId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getCategory(@PathVariable String categoryId, HttpServletResponse response) {
        Optional<Category> category = categoryRepo.findById(Integer.parseInt(categoryId));
        if(category.isEmpty()) {
            log.error("Category {} not found", categoryId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Category not found").build());
        }
        CategoryItems.CategoryItemsBuilder categoryItems = CategoryItems.builder();
        categoryItems.category(category.get());

        List<CategoryItem> categoryItemsRaw = categoryItemRepo.findAllByCategoryId(Integer.parseInt(categoryId));
        if(categoryItemsRaw.isEmpty()) {
            log.warn("No items found for category {}", categoryId);
        }

        List<Item> categoryDropItems = new ArrayList<>();
        for(CategoryItem categoryItem : categoryItemsRaw) {
            Optional<Item> item = itemRepo.findById(categoryItem.getItemId());
            if(item.isPresent()) {
                log.debug("Item {} with id {} found for category {}", item.get().getName(), categoryItem.getItemId(), categoryId);
                categoryDropItems.add(item.get());
            } else {
                log.warn("Item with id {} not found for category {}", categoryItem.getItemId(), categoryId);
            }
        }

        categoryItems.items(categoryDropItems);
        return Util.toJson(categoryItems.build());
    }
}
