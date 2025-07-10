package com.tenco.web.user;

import com.tenco.web._core.config.NaverProperties;
import com.tenco.web.utis.Define;
import com.tenco.web.utis.Validate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final NaverProperties naverProperties;

    @GetMapping("/user/signup-form")
    public String signUpForm() {
        log.info("회원가입 요청 폼");
        return "user/user-signup-form";
    }

    @PostMapping("/user/signup-form")
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

    @PostMapping("/user/login-form")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        log.info("로그인 시도...");
        User user = userService.login(loginDTO);
        session.setAttribute(Define.DefineMessage.SESSION_USER, user);
        return "redirect:/";
    }

    @GetMapping("/user/naver-login")
    public void naverLogin(HttpSession session, HttpServletResponse response) {
        log.info("네이버 로그인 요청");

        String state = UUID.randomUUID().toString();
        session.setAttribute("state", state);

        NaverProperties.Client client = naverProperties.getClient();

        if (client == null) {
            throw new RuntimeException("네이버 클라이언트 정보가 설정되지 않았습니다. yml 설정이 올바른지 확인해주세요.");
        }

        try {
            String authUrl = "https://nid.naver.com/oauth2.0/authorize"
                    + "?response_type=code"
                    + "&client_id=" + client.getId()
                    + "&redirect_uri=" + URLEncoder.encode(naverProperties.getRedirectUri(), "UTF-8")
                    + "&state=" + state;

            response.sendRedirect(authUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/oauth/login")
    public String callback(@RequestParam String code,
                           @RequestParam String state,
                           @RequestParam(required = false) String error,
                           HttpSession session) {

        // 네이버가 에러를 반환한 경우 (예: 사용자가 동의 안 함)
        if (error != null) {
            log.error("Naver login error: " + error);
            return "redirect:/login?error=naver_error";
        }

        // 1. state 값 검증
        String sessionState = (String) session.getAttribute("state");
        if (sessionState == null || !state.equals(sessionState)) {
            // 비정상적인 접근으로 간주하고 로그인 페이지로 리다이렉트
            return "redirect:/login?error=invalid_state";
        }

        // 사용한 state는 즉시 제거
        session.removeAttribute("state");

        // 1. 토큰 요청
        String tokenUrl = "https://nid.naver.com/oauth2.0/token"
                + "?grant_type=authorization_code"
                + "&client_id=" + naverProperties.getClient().getId()
                + "&client_secret=" + naverProperties.getClient().getSecret()
                + "&code=" + code
                + "&state=" + state;

        RestTemplate restTemplate = new RestTemplate();
        Map tokenResponse = restTemplate.getForObject(tokenUrl, Map.class);
        String accessToken = (String) tokenResponse.get("access_token");

        // 2. 사용자 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> profile = (Map<String, Object>) userResponse.getBody().get("response");
        String email = (String) profile.get("email");
        String name = (String) profile.get("name");

        User user = userService.findOrCreateUserByNaver(email, name);
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

        User updateUser = userService.updateById(userId,updateDTO, sessionUser);
        log.info("회원정보 수정완료");
        session.setAttribute(Define.DefineMessage.SESSION_USER, updateUser);
        return "redirect:/";
    }
}