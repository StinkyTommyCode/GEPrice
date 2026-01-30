package com.geprice.repository;

import com.geprice.pojo.Item;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<@NonNull Item, @NonNull Integer> {
    List<Item> findAllByNameUpperContaining(String name, Pageable pageable);
}