package com.tenco.web._core.errors.interceptor;

import com.tenco.web._core.errors.exception.Exception401;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component // IoC 대상 - 싱글톤 패턴
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * preHandle 메서드는 컨트롤러에 들어가기 전에 동작하는 메서드이다.
     * return 타입이 boolean(논리값)
     * true -> 컨트롤러에 정상 진입
     * false -> 못들어감
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (sessionUser == null) {
            throw new Exception401(Define.ErrorMessage.REQUIRED_LOGIN);
        }

        return true;
    }

    // 뷰가 렌더링 되기 전에 콜백 되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 뷰가 완전 렌더링 된 후 호출될 수 있다.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
