package com.tenco.web._core.errors;

import com.tenco.web._core.errors.exception.*;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {
        log.warn(Define.ErrorMessage.ERROR_400);
        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e, HttpServletRequest request) {
        log.warn(Define.ErrorMessage.ERROR_401);
        request.setAttribute("msg", e.getMessage());
        return "err/401";
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, HttpServletRequest request) {
        log.warn(Define.ErrorMessage.ERROR_403);
        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request) {
        log.warn(Define.ErrorMessage.ERROR_404);
        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {
        log.warn(Define.ErrorMessage.ERROR_500);
        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    // 아래 부터는 작은 예외 처리(로그인 실패 등등)
    // 로그인 오류
    @ExceptionHandler(UserLoginException.class)
    public String UserLoginEx(UserLoginException e, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.warn(Define.ErrorMessage.USER_LOGIN_ERROR);
        redirectAttributes.addFlashAttribute("errMsg", e.getMessage());
        return "redirect:/user/login-form";
    }

    @ExceptionHandler(CompanyLoginException.class)
    public String CompanyLoginEx(CompanyLoginException e, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        log.warn(Define.ErrorMessage.COMPANY_LOGIN_ERROR);
        redirectAttributes.addFlashAttribute("errMsg", e.getMessage());
        return "redirect:/company/login-form";
    }
}
