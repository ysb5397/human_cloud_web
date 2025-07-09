package com.tenco.web.community;

import com.tenco.web._core.common.PageLink;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 커뮤니티 글 작성
    @GetMapping("/community/job-seeker/write")
    public String jobSeekerSave(HttpSession session) {
        User user = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);

        if (user == null) {
            return "redirect:/user/login-form";
        }

        return "community/job-seeker-save-form";
    }

    // 커뮤니티 글 등록 기능 요청
    @PostMapping("/community/job-seeker-save-form")
    public String jobSeekerSaveForm(@Valid CommunityRequest.SaveDTO saveDTO, BindingResult result, HttpSession session, Model model){
        log.info("커뮤니티 글 등록 기능 요청");
        model.addAttribute(Define.DefineMessage.SAVE_DTO, saveDTO);
        log.info("model 객체에 saveDTO 저장");

        Map<String, String> errMap = new HashMap<>();
        if(result.hasErrors()){
            log.info("에러발견");
            for(FieldError error : result.getFieldErrors()) {
                errMap.put(error.getField(), error.getDefaultMessage());
                log.info("필드명 : {} / 오류메세지 : {}", error.getField(), error.getDefaultMessage());
            }
            log.info("errMap에 값 담기 성공");
            model.addAttribute("message", errMap);
            log.info("model 객체에 errMap 저장");
            return "community/job-seeker-save-form";
        }
        User sessionUser = (User) session.getAttribute("sessionUser");
        communityService.save(saveDTO, sessionUser);
        return "redirect:/community/job-seeker";
    }

    // 커뮤니티 구직자 목록 화면 요청
    @GetMapping("/community/job-seeker")
    public String jobSeekerList(Model model,
                                HttpSession session,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {

        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        if(sessionUser == null){
            return "redirect:/login-form";
        }
        log.info("목록화면요청 {}", sessionUser);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<Community> communities = communityService.findAll(pageable);

        // 페이지 네비게이션용 데이터 준비
        List<PageLink> pageLinks = new ArrayList<>();

        for (int i = 0; i < communities.getTotalPages(); i++) {
            pageLinks.add(new PageLink(i, i + 1, i == communities.getNumber()));
        }

        Integer previousPageNumber = communities.hasPrevious() ? communities.getNumber() : null;
        Integer nextPageNumber = communities.hasNext() ? communities.getNumber() + 2 : null;

        // 뷰 화면에 데이터 전달
        model.addAttribute("communities", communities);

        // 페이지 네비게이션에 사용할 번호 링크 리스트
        model.addAttribute("pageLinks", pageLinks);

        // 이전 페이지 번호 전달
        model.addAttribute("previousPageNumber", previousPageNumber);

        // 다음 페이지 번호 전달
        model.addAttribute("nextPageNumber", nextPageNumber);

        log.info("화면에 전달할 게시글: {}", communities);
        return "community/job-seeker";
    }

    // 커뮤니티 구직자 목록 상세 보기 화면 요청
    @GetMapping("/job-seeker-detail/{id}")
    public String Detail(@PathVariable(name = "id") int id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.DefineMessage.SESSION_USER);
        Community jobSeekerDetail = communityService.findByIdWithReplies(id, sessionUser);
        log.info("화면에 전달할 게시글: {}", jobSeekerDetail);
        model.addAttribute("community", jobSeekerDetail);
        model.addAttribute("replyCount", jobSeekerDetail.getReplies().size());
        return "community/job-seeker-detail";
    }



}
