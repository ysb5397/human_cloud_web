package com.tenco.web.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
