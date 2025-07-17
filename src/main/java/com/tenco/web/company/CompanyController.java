package com.tenco.web.company;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import com.tenco.web.utis.Validate;
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

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final Logger log = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService companyService;

    @GetMapping("/company/signup-form")
    public String signUpForm() {
        log.info("회원가입 요청 폼");
        return "company/company-signup-form";
    }

    @PostMapping("/company/signup")
    public String signUp(@Valid CompanyRequest.JoinDTO joinDTO, BindingResult result, Model model) {
        log.info("회원가입 시도");
        model.addAttribute(Define.DefineMessage.JOIN_DTO, joinDTO);

        Validate.CompanyValidate.checkJoinDTO(joinDTO, result);

        Map<String, String> errorMap = new HashMap<>();
        Company company = companyService.findByCompanyName(joinDTO);

        if (joinDTO.getCompanyName() != null && !joinDTO.getCompanyName().trim().isEmpty() && company == null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_COMPANY_NAME_CHECK, Define.NormalMessage.NOT_EXIST_COMPANY);
        } else if (joinDTO.getCompanyName() != null && !joinDTO.getCompanyName().trim().isEmpty() && company != null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_COMPANY_NAME, Define.ErrorMessage.EXIST_COMPANY);
            return "company/company-signup-form";
        }

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            model.addAttribute("message", errorMap);
            return "company/company-signup-form";
        }

        companyService.join(joinDTO);
        return "redirect:/company/login-form";
    }

    @GetMapping("/company/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "company/login-form";
    }

    @PostMapping("/company/login")
    public String login(CompanyRequest.LoginDTO loginDTO, HttpSession session) {
        Company company = companyService.login(loginDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, company);
        return "redirect:/";
    }

    @GetMapping("/company/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 시도");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping({"/companyinfo"})
    public String companyInfo(Model model,
                              HttpSession session) {

        Object obj = session.getAttribute(Define.DefineMessage.SESSION_USER);
        List<Company> companyList = null;

        if (obj instanceof User sessionUser) {
            companyList = companyService.findAll(sessionUser.getId());
        } else if (obj instanceof Company) {
            companyList = companyService.findAll();
        } else {
            return "redirect:/user/login-form";
        }

        model.addAttribute("companyList", companyList);
        model.addAttribute(Define.DefineMessage.SESSION_USER);
        return "company/companyinfo";
    }

    // 수정하기 화면 요청
    @GetMapping("/company/{id}/company-update-form")
    public String updateForm(@PathVariable(name = "id") int companyId,
                             Model model, HttpSession session){
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);

        model.addAttribute("updateDTO", companyService.findById(companyId));

        return "company/company-update-form";

    }



    // 수정하기 기능 요청
    @PostMapping("/company/{id}/company-update-form")
    public String update(@PathVariable(name = "id") int companyId, @Valid CompanyRequest.UpdateDTO updateDTO,
                         BindingResult result, HttpSession session, Model model) {
        log.info("회원 정보 수정 기능 요청");
        model.addAttribute(Define.DefineMessage.UPDATE_DTO, updateDTO);

        Validate.CompanyValidate.checkUpdateDTO(updateDTO, result);
        Company sessionCompany = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        Map<String, String> errorMap = new HashMap<>();
        Company company = companyService.findByUpdateCompanyName(updateDTO);
        if (sessionCompany == null) {
            log.warn("세션에 회사 정보 없음. 로그인 상태 확인 필요.");
            return "redirect:/login-form";
        }

        if (updateDTO.getCompanyName() != null && !updateDTO.getCompanyName().trim().isEmpty() && company == null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_COMPANY_NAME_CHECK, Define.NormalMessage.NOT_EXIST_COMPANY);
        } else if (updateDTO.getCompanyName() != null && !updateDTO.getCompanyName().trim().isEmpty() && company != null && !updateDTO.getCompanyName().equals(sessionCompany.getCompanyName())) {
            model.addAttribute(Define.DefineMessage.MESSAGE_COMPANY_NAME, Define.ErrorMessage.EXIST_COMPANY);
            return "company/company-update-form";
        }

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            model.addAttribute("message", errorMap);
            return "company/company-update-form";

        }

        companyService.updateById(companyId,updateDTO, sessionCompany);
        log.info("회원정보 수정완료");
        return "redirect:/index";
    }

}