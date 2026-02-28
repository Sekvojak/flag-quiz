package com.dominik.flag_quiz;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlagService {

    private final ResourceLoader resourceLoader;

    public FlagService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> listFlagCodes() {
        try {
            Resource res = resourceLoader.getResource("classpath:static/flags/");
            URL url = res.getURL();

            // Pozor: pri spustení z IDE je to normálny priečinok.
            // (Na jar packaging to riešime neskôr, zatiaľ je to OK.)
            File dir = new File(url.toURI());

            if (!dir.exists() || !dir.isDirectory()) {
                return List.of();
            }

            return Files.list(dir.toPath())
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.getFileName().toString())
                    .filter(name -> name.endsWith(".png")) // zatiaľ len png
                    .map(name -> name.substring(0, name.length() - 4)) // "sk.png" -> "sk"
                    .sorted()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}