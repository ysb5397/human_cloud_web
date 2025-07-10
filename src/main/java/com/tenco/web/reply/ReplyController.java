package com.tenco.web.reply;

import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final Logger log = LoggerFactory.getLogger(ReplyController.class);
    private final ReplyService replyService;

    @PostMapping("/reply/save")
    public String save(@RequestParam(name = "id") int id,
                       @Valid ReplyRequest.SaveDTO saveDTO,
                       BindingResult result,
                       RedirectAttributes redirect,
                       HttpSession session) {
        Object sessionObj = session.getAttribute(Define.DefineMessage.SESSION_USER);
        User user = null;

        if (sessionObj instanceof User) {
            user = (User) sessionObj;
        } else {
            log.warn("구직자만 접근 가능한 서비스입니다.");
            throw new Exception403("구직자만 접근 가능한 서비스입니다.");
        }

        Map<String, String> errorMap = new HashMap<>();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            redirect.addFlashAttribute("message", errorMap);
            log.info("전달할 오류 메시지 : {}", errorMap);
            return "redirect:/job-seeker-detail/" + id;
        }

        replyService.save(id, saveDTO, user);
        return "redirect:/job-seeker-detail/" + id;
    }

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable(name = "id") int id,
                         @RequestParam(name = "communityId") int communityId,
                         HttpSession session) {
        Object sessionObj = session.getAttribute(Define.DefineMessage.SESSION_USER);
        User user = null;

        if (sessionObj instanceof User) {
            user = (User) sessionObj;
        } else {
            log.warn("구직자만 접근 가능한 서비스입니다.");
            throw new Exception403("구직자만 접근 가능한 서비스입니다.");
        }

        replyService.deleteByUserId(id, user);
        return "redirect:/job-seeker-detail/" + communityId;
    }
}
