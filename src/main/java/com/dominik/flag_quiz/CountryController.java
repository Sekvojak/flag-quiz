package com.dominik.flag_quiz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    private final CountryNameService nameService;

    public CountryController(CountryNameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping("/api/countries")
    public List<Country> countries() {
        return nameService.codes().stream()
                .sorted()
                .map(code -> new Country(nameService.getNameOrFallback(code), code))
                .toList();
    }
}