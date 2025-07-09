package com.tenco.web.apply;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ApplyController {

    private ApplyService applyService;

    @PostMapping("/submit")
    public String submitResume(@RequestParam("resumeId") int resumeId,
                               @RequestParam("announceId") int announceId, HttpSession session){

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null) {
            return "redirect:/user/login-form";
        }
        applyService.submitResume(sessionUser.getId(), resumeId, announceId);

        return "redirect:/user/apply";
    }
}
