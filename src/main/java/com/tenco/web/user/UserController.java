package com.tenco.web.user;

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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

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
    public String signUp(@Valid UserRequest.JoinDTO joinDTO, BindingResult result, Model model) {
        log.info("회원가입 시도...");
        model.addAttribute(Define.DefineMessage.JOIN_DTO, joinDTO);

        Validate.UserValidate.checkPassword(joinDTO, result);

        Map<String, String> errorMap = new HashMap<>();
        User user = userService.findByUsername(joinDTO);
        if (joinDTO.getUsername() != null && !joinDTO.getUsername().trim().isEmpty() && user == null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_USERNAME_CHECK, Define.NormalMessage.NOT_EXIST_USER);
        } else if (joinDTO.getUsername() != null && !joinDTO.getUsername().trim().isEmpty() && user != null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_USERNAME, Define.ErrorMessage.EXIST_USER);
            return "user/user-signup-form";
        }

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            model.addAttribute("message", errorMap);
            return "user/user-signup-form";
        }

        userService.join(joinDTO);
        log.info("회원가입 완료!");
        return "system/login-form";
    }

    @GetMapping("/user/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "system/login-form";
    }

    @PostMapping("/user/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        log.info("로그인 시도...");
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, user);
        return "redirect:/";
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

        updateDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        User updateUser = userService.updateById(sessionUser.getId(), updateDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, updateUser);

        return "redirect:/user/mypage-form";
    }

    @GetMapping("/user/resume-update")
    public String resumeUpdate(HttpSession session){
        User sessionUser = (User)session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null) {
            return "redirect:/login-form";
        }
        return "user/resume-update";
    }
}