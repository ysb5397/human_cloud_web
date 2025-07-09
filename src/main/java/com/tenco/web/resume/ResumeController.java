package com.tenco.web.resume;

import com.tenco.web.tags.SkillTagService;
import com.tenco.web.tags.resume_tag.ResumeSkillTagRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ResumeController {

    private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
    private final ResumeService resumeService;
    private final SkillTagService skillTagService;

    // 이력서 등록 화면 요청
    @GetMapping("/user/resume-register")
    public String resumeRegister(HttpSession session, Model model){
        User sessionUser = (User)session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null) {
            return "redirect:/login-form";
        }

        model.addAttribute("skillTagList", skillTagService.findAll());
        return "user/resume-register";
    }

    // 이력서 저장 기능 요청
    @PostMapping("/user/resume-register")
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
            return "user/resume-register";
        }

        User sessionUser = (User) session.getAttribute("sessionUser");
        saveDTO.setIsPublic(true);
        resumeService.save(saveDTO, sessionUser);
        return "redirect:/resume-list";
    }

    // 이력서 목록 보기 화면 요청
    @GetMapping("/resume-list")
    public String resumeList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        List<Resume> resumeList = resumeService.findByUserId(sessionUser.getId());
        model.addAttribute("resumeList", resumeList);
        return "user/resume-list";
    }

    // 이력서 상세보기 화면 요청
    @GetMapping("/resume-detail/{id}")
    public String detail(@PathVariable(name = "id") int id, Model model) {
        Resume resumeDetail = resumeService.findById(id);
        log.info("화면에 전달할 이력서: {}", resumeDetail);
        model.addAttribute("resume", resumeDetail);
        return "user/resume-detail";
    }

    // 이력서 삭제 기능 처리
    @PostMapping("/resume-list/{id}/delete-form")
    public String delete(@PathVariable(name = "id") int id, HttpSession session){

        log.info("이력서 삭제 요청 - ID : {}",id);
        User sessionResume = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        resumeService.deleteById(id, sessionResume);

        return "redirect:/";
    }

    // 이력서 수정하기 화면 요청
    @GetMapping("/user/{id}/resume-update")
    public String updateForm(@PathVariable(name = "id") int resumeId,
                             Model model, HttpSession session) {
        User sessionResume = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        resumeService.checkResumeOwner(resumeId, sessionResume.getId());

        List<ResumeSkillTagRequest.CheckDTO> skillTagList = resumeService.getSkillTagsForUpdate(resumeId);

        model.addAttribute("resume", resumeService.findById(resumeId));
        model.addAttribute("skillTagList", skillTagList);

        return "user/resume-update";
    }

    // 이력서 수정 기능 처리
    @PostMapping("/user/{id}/resume-update")
    public String update(@PathVariable(name = "id") int resumeId,
                         ResumeRequest.UpdateDTO reqDTO, HttpSession session, Model model){

        System.out.println("수정할 태그: " + reqDTO.getSkillTags());

        User sessionResume = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        Resume resume = resumeService.UpdateById(resumeId,reqDTO,sessionResume);
        model.addAttribute("resume", resume);

        return "redirect:/resume-detail/" + resumeId;
    }
}
