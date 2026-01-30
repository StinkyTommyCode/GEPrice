package com.geprice.repository;

import com.geprice.Util;
import com.geprice.pojo.Boss;
import com.geprice.pojo.BossDrop;
import com.geprice.pojo.Item;
import com.geprice.pojo.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    CommandLineRunner initDatabase(BossRepo bossRepo, ItemRepo itemRepo, BossDropRepo bossDropRepo) {
        return args -> {
            if(bossRepo.count() == 0){
                initBossRepo(bossRepo);
            }

            if(itemRepo.count() == 0){
                initItemRepo(itemRepo);
            }

            if(bossDropRepo.count() == 0){
                initBossDropRepo(bossDropRepo);
            }
        };
    }

    private void initBossRepo(BossRepo bossRepo) throws FileNotFoundException {
        bossRepo.save(Boss.builder()
                .id(27795)
                .name("Arch-Glacor")
                .wikiUrl("https://runescape.wiki/w/Arch-Glacor")
                .icon("https://runescape.wiki/w/File:Arch-Glacor.png")
                .build());
    }

    private void initItemRepo(ItemRepo itemRepo) throws FileNotFoundException {
        itemRepo.save(Item.builder()
                .id(52115)
                .name("Scripture of Wen")
                .nameUpper("Scripture of Wen".toUpperCase(Locale.UK))
                .description("A collection of manuscripts detailing as much as is known about the Elder God, Wen.")
                .type("Miscellaneous")
                .icon("https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_sprite.gif?id=52115")
                .iconLarge("https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_big.gif?id=52115")
                .wikiUrl("https://runescape.wiki/w/Scripture_of_Wen")
                .isMembers(true)
                .build());

        itemRepo.save(Item.builder()
                .id(51817)
                .name("Manuscript of Wen")
                .nameUpper("Manuscript of Wen".toUpperCase(Locale.UK))
                .description("Adds 45 minutes of charge to a Scripture of Wen.")
                .type("Miscellaneous")
                .icon("https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_sprite.gif?id=51817")
                .iconLarge("https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_big.gif?id=51817")
                .wikiUrl("https://runescape.wiki/w/Scripture_of_Wen")
                .isMembers(true)
                .build());
    }

    private void initBossDropRepo(BossDropRepo bossDropRepo) throws FileNotFoundException {
        bossDropRepo.save(BossDrop.builder()
                .bossId(27795)
                .itemId(52115)
                .build());

        bossDropRepo.save(BossDrop.builder()
                .bossId(27795)
                .itemId(51817)
                .build());
    }
}
