package com.tenco.web.companySub;

import com.tenco.web.company.Company;
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
public class CompanySubController {

    private final CompanySubService companySubService;

    @PostMapping("/company/companysub")
    public String sub(@RequestParam(name = "id") int userId,
                      HttpSession session) {
        Company company = (Company)session.getAttribute(Define.DefineMessage.SESSION_USER);

        if(company == null){
            return "redirect:/login-form";
        }
        companySubService.companySubscribe(company.getId(), userId);
        return "redirect:/userdetail/" + userId;
    }

    @GetMapping("/company/subuserlist")
    public String subUserList(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if (company == null){
            return "redirect:/login-form";
        }
        model.addAttribute("subuserlist", companySubService.getSubscribedUsers(company.getId()));
        return "company-sub-list";
    }
}
