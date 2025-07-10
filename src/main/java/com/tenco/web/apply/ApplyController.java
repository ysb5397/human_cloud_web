package com.tenco.web.apply;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;


    // 이력서 제출 기능
    @PostMapping("/submit")
    public String submitResume(ApplyRequest.SaveDTO saveDTO, HttpSession session){

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null) {
            return "redirect:/user/login-form";
        }
        applyService.submitResume(saveDTO, sessionUser);

        return "redirect:/user";
    }

    // 특정 유저가 제출한 지원 목록 보기
    @GetMapping("/my-apply-list")
    public String applyList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        List<Apply> applyList = applyService.findByUserId(sessionUser.getId());
        model.addAttribute("applyList", applyList);
        return "user/my-apply-list";
    }


}
