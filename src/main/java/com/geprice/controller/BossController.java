package com.geprice.controller;

import com.geprice.Util;
import com.geprice.pojo.Boss;
import com.geprice.pojo.GEPriceError;
import com.geprice.repository.BossRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bosses")
public class BossController {

    private final BossRepo bossRepo;

    public BossController(BossRepo bossRepo) {
        this.bossRepo = bossRepo;
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getBoss(@PathVariable String id, HttpServletResponse response) {
        Optional<Boss> boss = bossRepo.findById(Integer.parseInt(id));
        if(boss.isPresent()) {
            return Util.toJson(boss.get());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Boss not found").build());
        }
    }

    @GetMapping("/all")
    public List<Boss> getAll()
    {
        return bossRepo.findAll();
    }
}
