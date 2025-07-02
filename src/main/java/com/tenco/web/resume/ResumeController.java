package com.tenco.web.resume;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ResumeController {

    private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
    private final ResumeService resumeService;

    // 이력서 저장 기능 요청
    @PostMapping("/user/resume-update")
    public String save(@Valid ResumeRequest.SaveDTO saveDTO, BindingResult result, HttpSession session, Model model) {
        log.info("이력서 저장 기능 요청");
        model.addAttribute(Define.DefineMessage.SAVE_DTO, saveDTO);
        log.info("model 객체에 saveDTO 저장");

        Map<String, String> errorMap = new HashMap<>();
        if (result.hasErrors()) {
            log.info("에러 발견");
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("필드명 : {} / 오류 메시지 : {}", error.getField(), error.getDefaultMessage());
            }
            log.info("errorMap에 값 담기 성공");
            model.addAttribute("message", errorMap);
            log.info("model 객체에 errorMap 저장");
            return "user/resume-update";
        }

        User sessionUser = (User) session.getAttribute("sessionUser");
        saveDTO.setIsPublic(true);
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
