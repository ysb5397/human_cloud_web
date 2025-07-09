package com.tenco.web.main;

import com.tenco.web._core.common.PageLink;
import com.tenco.web.announce.Announce;
import com.tenco.web.community.Community;
import com.tenco.web.community.CommunityService;
import com.tenco.web.tags.SkillTagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final MainService mainService;
    private final SkillTagService skillTagService;
    private final CommunityService communityService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Announce> announceList = mainService.findAll();
        model.addAttribute("announceList", announceList);
        return "index";
    }

    @GetMapping("/community/job-seekers")
    public String jobSeekersCommunity(Model model,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size) {

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

        return "community/job-seeker";
    }
}
