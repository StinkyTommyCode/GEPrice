package com.geprice.repository;

import com.geprice.pojo.Boss;
import com.geprice.pojo.BossDrop;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BossDropRepo extends JpaRepository<@NonNull BossDrop, @NonNull Long> {
    List<BossDrop> findAllByBossId(int bossId);
}