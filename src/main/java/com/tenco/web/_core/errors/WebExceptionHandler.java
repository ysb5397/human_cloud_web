package com.tenco.web._core.errors;

import com.tenco.web._core.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {

        log.warn("=== 400 Bad Request Error 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    @ResponseBody
    public ResponseEntity<String> ex401ByData(Exception401 e, HttpServletRequest request) {
        String script = "<script> " +
                "alert('" +
                e.getMessage() +
                "'); " +
                "location.href='/login-form'; " +
                "</script>";

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.TEXT_HTML)
                .body(script);
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, HttpServletRequest request) {

        log.warn("=== 403 Forbidden Error 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request) {

        log.warn("=== 404 Not Found Error 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {

        log.warn("=== 500 Internal Server Error 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    // 아래 부터는 작은 예외 처리(로그인 실패 등등)
    // 로그인 오류
    @ExceptionHandler(LoginException.class)
    public String LoginEx(LoginException e, HttpServletRequest request, Model model) {
        log.warn("=== 로그인 오류 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        model.addAttribute("errMsg", e.getMessage());

        return "system/login-form";
    }

    // 회원가입 오류
    @ExceptionHandler(UserJoinException.class)
    public String JoinEx(UserJoinException e, HttpServletRequest request, Model model) {
        log.warn("=== 로그인 오류 발생 ===");
        log.warn("요청 url : {}", request.getRequestURI());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-Agent : {}", request.getHeader("User-Agent"));

        model.addAttribute("errMsg", e.getMessage());

        return "user/login-form";
    }
}
