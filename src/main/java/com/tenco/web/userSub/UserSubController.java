package com.tenco.web.userSub;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserSubController {

    private final UserSubService userSubService;

    @PostMapping("/userSub/Usersub")
    public String sub(@RequestParam(name = "id") int companyId,
                      HttpSession session) {
        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (user == null) {
            return "redirect:/login-form";
        }

        userSubService.Usersubscribe(user.getId(), companyId);
        return "redirect:/companydetail/" + companyId;
    }

//    @GetMapping("/userSub/subcompanylist")
//    public String subcompanylist(HttpSession session, Model model) {
//        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
//
//        if (user == null) return "redirect:/login-form";
//
//    }

}
