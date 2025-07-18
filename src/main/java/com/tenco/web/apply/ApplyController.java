package com.tenco.web.apply;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    // 특정 유저가 제출한 지원 목록 보기
    @GetMapping("/user/my-apply-list")
    public String applyList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        List<Apply> applyList = applyService.findByUserId(sessionUser.getId());
        model.addAttribute("applyList", applyList);
        return "user/my-apply-list";
    }

    // 특정 기업에 제출한 지원 목록 보기
    @GetMapping("/company/apply-list")
    public String comApplyList(Model model, HttpSession session) {
        Company sessionUser = (Company) session.getAttribute(Define.DefineMessage.SESSION_USER);
        List<Apply> applyList =  applyService.findByCompanyId(sessionUser.getId());
        model.addAttribute("applyList", applyList);
        return "company/apply-list";
    }

    @PostMapping("/company/apply/cancel")
    public String applyCancel(@RequestParam(name = "id") int userId,
                              HttpSession session) {
        Object obj = session.getAttribute(Define.DefineMessage.SESSION_USER);

        if(obj instanceof Company company) {
            applyService.companyApplyCancel(company.getId(), userId);
            return "redirect:/company/apply-list";
        }
        return "redirect:/login-from";
    }
}
