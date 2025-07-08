package com.tenco.web.reply;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply/save")
    public String save(@RequestParam(name = "id") int id,
                       @Valid ReplyRequest.SaveDTO saveDTO,
                       BindingResult result,
                       HttpSession session) {
        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (user == null) {
            return "redirect:/login-form";
        }
        replyService.save(id, saveDTO, user);

        return "redirect:/job-seeker-detail/" + id;
    }

}
