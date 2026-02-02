package com.geprice.controller;

import com.geprice.Constants;
import com.geprice.Util;
import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.Boss;
import com.geprice.repository.BossRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bosses")
public class BossController {

    private static final Logger log = LoggerFactory.getLogger(BossController.class);

    private final BossRepo bossRepo;

    public BossController(BossRepo bossRepo) {
        this.bossRepo = bossRepo;
    }

    @GetMapping("/{id}")
    public Boss getBoss(@PathVariable String id) {
        int bossId = Util.validateIntegerParameter(id, Constants.BOSS_NOT_FOUND);

        Optional<Boss> boss = bossRepo.findById(bossId);
        if(boss.isPresent()) {
            log.debug("Boss found: {}", boss.get().getName());
            return boss.get();
        } else {
            log.warn("Boss not found: {}", id);
            throw new GEPrice404Error(Constants.BOSS_NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<Boss> getAll()
    {
        return bossRepo.findAll();
    }
}
