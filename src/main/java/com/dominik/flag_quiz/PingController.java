package com.dominik.flag_quiz;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PingController {

    private final FlagService flagService;

    public PingController(FlagService flagService) {
        this.flagService = flagService;
    }

    @GetMapping("api/flags")
    public List<String> flags() {
        return flagService.listFlagCodes();
    }
}
