package com.tenco.web.main;

import com.tenco.web.announce.Announce;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final MainService mainService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Announce> announceList = mainService.findAll();
        model.addAttribute("announceList", announceList);
        return "index";
    }
}
