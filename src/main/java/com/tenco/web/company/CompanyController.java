package com.tenco.web.company;

import com.tenco.web.announce.Announce;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String signUp(CompanyRequest.JoinDTO joinDTO) {
        log.info("회원가입 시도");
        joinDTO.validate();
        companyService.join(joinDTO);
        return "redirect:/company/login-form";
    }

    @GetMapping("/company/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "system/login-form";
    }

    @PostMapping("/company/login")
    public String login(CompanyRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
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
    public String companyInfo(Model model) {
        List<Company> companyList = companyService.findAll();
        model.addAttribute("companyList",companyList);
        return "company/companyinfo";
    }

}