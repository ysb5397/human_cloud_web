package com.tenco.web._core.errors;

import com.tenco.web._core.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 에러 페이지로 연결 처리
// @RestControllerAdvice 데이터를 반환 할 때
public class WebExceptionHandler {

    // slf4j 로거 생성 - 로깅 사용 시 System.out.println() 대신 사용
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

//    @ExceptionHandler(Exception401.class)
//    public String ex401(Exception401 e, HttpServletRequest request) {
//
//        log.warn("=== 401 Unauthorized Error 발생 ===");
//        log.warn("요청 url : {}", request.getRequestURI());
//        log.warn("인증 오류 : {}", e.getMessage());
//        log.warn("User-Agent : {}", request.getHeader("User-Agent"));
//
//        request.setAttribute("msg", e.getMessage());
//        return "err/401";
//    }

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
}
