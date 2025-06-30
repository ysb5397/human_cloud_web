package com.tenco.web.user;

import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/user/signup-form")
    public String signUpForm() {
        log.info("회원가입 요청 폼");
        return "user/user-signup-form";
    }

    @PostMapping("/user/signup")
    public String signUp(UserRequest.JoinDTO joinDTO, Model model) {
        log.info("회원가입 시도...");
        String errMsg = joinDTO.validate();
        model.addAttribute("errMsg", errMsg);
        userService.join(joinDTO);
        log.info("회원가입 완료!");
        return errMsg == null ? "user/login-form" : "user/signup-form";
    }

    @GetMapping("/user/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "user/login-form";
    }

    @PostMapping("/user/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session, Model model) {
        log.info("로그인 시도...");
        String errMsg = loginDTO.validate();
        model.addAttribute("errMsg", errMsg);
        User user = userService.login(loginDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, user);
        log.info("로그인 완료!");
        return errMsg == null ? "redirect:/" : "user/login-form";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        log.info("로그아웃 완료");
        return "redirect:/";
    }

    @GetMapping("/user/update-form")
    public String updateForm(Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        User user = userService.findById(sessionUser.getId());

        model.addAttribute("user", user);
        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO, HttpSession session, Model model) {

        String errMsg = updateDTO.validate();
        model.addAttribute("errMsg", errMsg);
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        User updateUser = userService.updateById(sessionUser.getId(), updateDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, updateUser);

        return errMsg == null ? "redirect:/user/mypage-form" : "user/update-form";
    }
}