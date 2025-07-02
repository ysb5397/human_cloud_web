package com.tenco.web.announce;

import com.tenco.web.company.Company;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnnounceController {

    private static final Logger log = LoggerFactory.getLogger(AnnounceController.class);

    //DI 처리
    private final AnnounceService announceService;

    // 게시글 삭제 액션 처리
    @PostMapping("/announceborderlist/{id}/delete-form")
    public String delete(@PathVariable(name = "id") int id, HttpSession session) {

        log.info("게시글 삭제 요청 - ID : {}", id);
        Company sessionCompany = (Company) session.getAttribute("sessionUser");
        announceService.deleteById(id,sessionCompany);

        return "redirect:/";
    }

}
