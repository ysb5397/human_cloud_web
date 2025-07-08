package com.tenco.web.announce;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web.company.Company;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnnounceController {

    private static final Logger log = LoggerFactory.getLogger(AnnounceController.class);

    //DI 처리
    private final AnnounceService announceService;


    // 공고 등록 화면 요청
    @GetMapping("/company/hire-register")
    public String save(HttpSession session, Model model) {
        log.info("공고 저장 기능 요청");
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if (sessionCompany == null) {
            return "redirect:/company/login-form";
        }

        model.addAttribute(Define.DefineMessage.SESSION_COMPANY, sessionCompany);
        return "company/hire-register";
    }

    // 공고 등록 액션 처리
    @PostMapping("/company/hire-register")
    public String registerJob(@Valid AnnounceRequest.SaveJobDTO saveJobDTO,
                              BindingResult result,
                              HttpSession session,
                              Model model) {

        log.info("공고 등록 POST 요청");
        log.info("model 객체 값 확인 : {}", saveJobDTO.toString());
        model.addAttribute(Define.DefineMessage.SAVE_JOB_DTO, saveJobDTO);

        Map<String, String> errorMap = new HashMap<>();
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if (result.hasErrors()) {
            log.info("에러 발견");
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("필드명 : {} / 오류메시지 : {}", error.getField(), error.getDefaultMessage());
            }
            model.addAttribute("message", errorMap);
            model.addAttribute(Define.DefineMessage.SESSION_COMPANY, sessionCompany);
            return "company/hire-register";
        }

        if (sessionCompany == null) {
            // 세션 만료 또는 미로그인 상태
            log.info("로그인안됨");
            return "redirect:/login";
        }

        announceService.save(saveJobDTO, sessionCompany);
        log.info("저장됨");
        return "redirect:/announceboardlist"; // 등록 후 공고 목록 페이지로 이동
    }

    // 공고 관리 화면 요청
    @GetMapping("/company/register-list")
    public String registerList(
            // 1. 인증검사를한다.(인터셉터에서 진행)
            // 2. 본인이 맞다면 announceborderlist에서 화면을 들고온다
            // 3. 그리고 그 중에서 내 pk 아이디와 맞는 화면을 들고온다.
            // 4. List로 해서 다 보여준다.
            HttpSession session, Model model) {
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if (sessionCompany == null) {
            return "redirect:/login-form";
        }
        List<Announce> announceList = announceService.findMyAnnounceList(sessionCompany.getId());
        model.addAttribute("announceList", announceList);
        return "company/register-list";

    }

    // 공고 수정하기 화면 요청
    @GetMapping("/company/{id}/hire-update")
    public String updateForm(@PathVariable(name = "id") int announceId,
                             Model model, HttpSession session) {
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        announceService.checkCoBoardOwner(announceId, sessionCompany.getId());

        model.addAttribute("announce",announceService.findAnnounceWithCompanyById(announceId));


        return "company/hire-update";
    }

    // 공고 수정 기능 처리
    @PostMapping("/company/{id}/hire-update")
    public String update(@PathVariable(name = "id") int announceId,
                         AnnounceRequest.UpdateDTO reqDTO, HttpSession session, Model model) {

        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        announceService.checkCoBoardOwner(announceId, sessionCompany.getId());
        announceService.UpdateById(announceId, reqDTO, sessionCompany);

        return "redirect:/announcedetail/" + announceId;
    }

    // 게시글 삭제 액션 처리
    @PostMapping("/announceboardlist/{id}/delete-form")
    public String delete(@PathVariable(name = "id") int id, HttpSession session) {

        log.info("게시글 삭제 요청 - ID : {}", id);
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        announceService.deleteById(id, sessionCompany);

        return "redirect:/";
    }
}
