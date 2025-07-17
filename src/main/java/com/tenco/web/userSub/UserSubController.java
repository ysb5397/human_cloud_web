package com.tenco.web.userSub;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserSubController {

    private final UserSubService userSubService;

    @PostMapping("/user/usersub")
    public String sub(@RequestParam(name = "id") int companyId,
                      HttpSession session) {
        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (user == null) {
            return "redirect:/login-form";
        }
        userSubService.userSubscribe(user.getId(), companyId);
        return "redirect:/companyinfo/" + companyId;
    }

    @GetMapping("/user/user-sub-list")
    public String subCompanyList(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (user == null) {
            return "redirect:/login-form";
        }
        model.addAttribute("subcompanylist", userSubService.getSubscribedCompanies(user.getId()));
        return "user/user-sub-list";

    }
}
