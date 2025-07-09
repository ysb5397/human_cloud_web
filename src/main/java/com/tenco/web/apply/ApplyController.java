package com.tenco.web.apply;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ApplyController {

    private ApplyService applyService;

    @PostMapping("/submit")
    public String submitResume(@RequestParam(name = "id") int id,
                               ApplyRequest.SaveDTO saveDTO, HttpSession session){

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null) {
            return "redirect:/user/login-form";
        }
        applyService.submitResume(id, saveDTO, sessionUser);

        return "redirect:/user/apply";
    }
}
