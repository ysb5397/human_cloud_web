package com.tenco.web.resume;

import com.tenco.web.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResumeController {

    private final ResumeService resumeService;

    // 이력서 저장 기능 요청
    @PostMapping("/user/resume-update")
    public String save(@Valid ResumeRequest.SaveDTO saveDTO, HttpSession session, BindingResult result, Model model) {
//        model.addAttribute()

        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.save(saveDTO, sessionUser);
        return "redirect:/resume-list";
    }

    @GetMapping("/resume-list")
    public String resumeList(Model model) {
        List<Resume> resumeList = resumeService.findAll();
        model.addAttribute("resumeList", resumeList);
        return "user/resume-list";
    }
}
