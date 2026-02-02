package com.geprice.repository;

import com.geprice.pojo.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<@NonNull Category, @NonNull Integer> { }
