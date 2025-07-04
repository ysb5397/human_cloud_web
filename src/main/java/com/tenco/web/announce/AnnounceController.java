package com.tenco.web.announce;

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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        announceService.deleteById(id, sessionCompany);

        return "redirect:/";
    }

    // 공고 등록 화면 요청
    @GetMapping("/company/hire-register")
    public String save(@Valid AnnounceRequest.SaveJobDTO saveJobDTO, BindingResult result,
                       HttpSession session, Model model) {
        log.info("공고 저장 기능 요청");
        model.addAttribute("saveJobDTO", saveJobDTO);
        log.info("model 객체에 saveJobDTO 저장");

        Map<String, String> errorMap = new HashMap<>();
        if (result.hasErrors()) {
            log.info("에러 발견");
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("필드명 : {} / 오류메시지 : {}", error.getField(), error.getDefaultMessage());
            }
            return "company/hire-register";
        }

        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        return "redirect:/hire-register";

    }


    // 공고 등록 액션 처리

    @PostMapping("/company/hire-register")
    public String registerJob(@Valid AnnounceRequest.SaveJobDTO saveJobDTO,
                              BindingResult result,
                              HttpSession session,
                              Model model) {

        log.info("공고 등록 POST 요청");



        // 2. 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        // 3. LocalDateTime으로 파싱
        LocalDateTime localDateTime = LocalDateTime.parse(saveJobDTO.getEndDateString(), formatter);
        saveJobDTO.setEndDate(Timestamp.valueOf(localDateTime));

        log.info("model 객체 값 확인 : {}", saveJobDTO.toString());

        if (result.hasErrors()) {
            model.addAttribute("saveJobDTO", saveJobDTO); // 다시 폼에 값 유지
            return "company/hire-register";
        }
        log.info("저장됨");

        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if (sessionCompany == null) {
            // 세션 만료 또는 미로그인 상태
            return "redirect:/login";
        }
        log.info("로그인안됨");

        announceService.save(saveJobDTO, sessionCompany);

        return "redirect:/announceborderlist"; // 등록 후 공고 목록 페이지로 이동
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

    //


}
