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

    @GetMapping("/join-form")
    public String joinForm() {
        log.info("회원가입 요청 폼");
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {

        joinDTO.validate();
        userService.join(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "user/login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 시도");
        session.invalidate();
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
    public String update(UserRequest.UpdateDTO updateDTO, HttpSession session) {

        updateDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        User updateUser = userService.updateById(sessionUser.getId(), updateDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, updateUser);

        return "redirect:/user/update-form";
    }
}