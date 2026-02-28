package com.dominik.flag_quiz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    private final FlagService flagService;
    private final CountryNameService nameService;

    public CountryController(FlagService flagService, CountryNameService nameService) {
        this.flagService = flagService;
        this.nameService = nameService;
    }

    @GetMapping("/api/countries")
    public List<Country> countries() {
        List<String> codes = flagService.listFlagCodes();
        return codes.stream()
                .filter(nameService::hasName)   // len tie, čo sú v JSON
                .map(code -> new Country(nameService.getNameOrFallback(code), code))
                .toList();
    }

}
