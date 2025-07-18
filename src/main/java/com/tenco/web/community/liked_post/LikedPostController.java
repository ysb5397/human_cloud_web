package com.tenco.web.community.liked_post;

import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LikedPostController {

    private final LikedPostService likedPostService;

    @PostMapping("/like/post")
    public String like(@RequestParam(name = "id") int communityId,
                       @RequestParam(name = "interestCount", defaultValue = "0") int interestCount,
                       HttpSession session) {

        Object obj = session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (obj instanceof User sessionUser) {
            sessionUser = (User) obj;
        } else {
            throw new Exception403("접근 권한이 없습니다.");
        }

        likedPostService.update(communityId, interestCount, sessionUser);
        return "redirect:/community/job-seeker";
    }
}
