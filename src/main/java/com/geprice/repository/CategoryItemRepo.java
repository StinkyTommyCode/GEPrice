package com.geprice.repository;

import com.geprice.pojo.CategoryItem;
import com.geprice.pojo.CategoryItemKey;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepo extends JpaRepository<@NonNull CategoryItem, @NonNull CategoryItemKey> {
    List<CategoryItem> findAllByCategoryId(int categoryId);
}
