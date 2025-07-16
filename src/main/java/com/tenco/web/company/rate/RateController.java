package com.tenco.web.company.rate;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/user/rating")
    public String rating(@RequestParam(name = "companyId") int companyId,
                         RateRequest.SaveDTO saveDTO, HttpSession session) {
        log.info("평점 남기기 요청 - saveDTO : {}", saveDTO);
        Object obj = session.getAttribute(Define.DefineMessage.SESSION_USER);
        User sessionUser = null;
        if (obj instanceof User) {
            sessionUser = (User) obj;
        }

        rateService.save(saveDTO, sessionUser, companyId);
        return "redirect:/companyinfo";
    }
}
