package com.r505.r505;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonjourController {

    @GetMapping("/bonjour")
    public String direBonjour() {
        return "Bonjour le monde !";
    }
}
