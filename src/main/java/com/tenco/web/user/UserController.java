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
import org.springframework.web.bind.annotation.PathVariable;
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

        Validate.UserValidate.checkJoinDTO(joinDTO, result);

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
        return "user/login-form";
    }

    @GetMapping("/user/login-form")
    public String loginForm() {
        log.info("로그인 요청 폼");
        return "user/login-form";
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

    // 수정하기 화면 요청
    @GetMapping("/user/{id}/user-update")
    public String updateForm(@PathVariable(name = "id") int userId,
                             Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        userService.checkUserOwner(userId, sessionUser.getId());

        UserRequest.UpdateDTO updateDTO = userService.getUserInfoById(sessionUser.getId());

        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("updateDTO", updateDTO);

        return "user/user-update";
    }

    // 수정하기 기능 요청
    @PostMapping("/user/{id}/user-update")
    public String update(@PathVariable(name = "id") int userId, @Valid UserRequest.UpdateDTO updateDTO,
                         BindingResult result, HttpSession session, Model model) {
        log.info("회원 정보 수정 기능 요청");
        model.addAttribute(Define.DefineMessage.UPDATE_DTO, updateDTO);

        Validate.UserValidate.checkUpdateDTO(updateDTO, result);
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        Map<String, String> errorMap = new HashMap<>();
        User user = userService.findByUpdateUsername(updateDTO);
        if (updateDTO.getUsername() != null && !updateDTO.getUsername().trim().isEmpty() && user == null) {
            model.addAttribute(Define.DefineMessage.MESSAGE_USERNAME_CHECK, Define.NormalMessage.NOT_EXIST_USER);
        } else if (updateDTO.getUsername() != null && !updateDTO.getUsername().trim().isEmpty() && user != null && !updateDTO.getUsername().equals(sessionUser.getUsername())) {
            model.addAttribute(Define.DefineMessage.MESSAGE_USERNAME, Define.ErrorMessage.EXIST_USER);
            return "user/user-update";
        }

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            model.addAttribute("message", errorMap);
            return "user/user-update";

    }

        userService.updateById(userId,updateDTO, sessionUser);
        log.info("회원정보 수정완료");
        return "redirect:/index";
    }


}