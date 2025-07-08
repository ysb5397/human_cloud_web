package com.tenco.web.community;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;


    // 커뮤니티 구직자 목록 화면 요청
    @GetMapping("/community/job-seeker")
    public String jobSeekerList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null){
            return "redirect:/login-form";
        }
        log.info("목록화면요청 {}", sessionUser);
        List<Community> jobSeekerList = communityService.findAll();
        model.addAttribute("jobSeekerList", jobSeekerList);
        log.info("화면에 전달할 게시글: {}", jobSeekerList);
        return "community/job-seeker";
    }

    // 커뮤니티 구직자 목록 상세 보기 화면 요청
    @GetMapping("/job-seeker-detail/{id}")
    public String Detail(@PathVariable(name = "id") int id, Model model) {
        Community jobSeekerDetail = communityService.findById(id);
        log.info("화면에 전달할 게시글: {}", jobSeekerDetail);
        model.addAttribute("community", jobSeekerDetail);
        return "community/job-seeker-detail";
    }



}
