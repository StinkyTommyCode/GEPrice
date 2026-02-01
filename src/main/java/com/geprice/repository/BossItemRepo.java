package com.geprice.repository;

import com.geprice.pojo.BossItem;
import com.geprice.pojo.BossItemKey;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BossItemRepo extends JpaRepository<@NonNull BossItem, @NonNull BossItemKey> {
    List<BossItem> findAllByBossId(int bossId);
}