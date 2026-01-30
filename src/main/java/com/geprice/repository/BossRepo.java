package com.geprice.repository;

import com.geprice.pojo.Boss;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BossRepo extends JpaRepository<@NonNull Boss, @NonNull Integer> { }