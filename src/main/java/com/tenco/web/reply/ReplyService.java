package com.tenco.web.reply;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web.community.Community;
import com.tenco.web.community.CommunityService;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final 키워드를 가진 멤버를 초기화 해
@Service // IoC 대상
public class ReplyService {

    private static final Logger log = LoggerFactory.getLogger(ReplyService.class);
    // DI 처리
    private final ReplyJpaRepository replyJpaRepository;
    private final CommunityService communityService;

    // 댓글 저장 기능
    // 서비스 계층, Repository 계층에 메서드 이름 (같이 하거나, 다르게 정의)
    public void save(int id, ReplyRequest.SaveDTO saveDTO, User user) {
        Community community = communityService.findById(id);
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .user(user)
                .community(community)
                .build();

        replyJpaRepository.save(reply);
    }

    // 댓글 삭제 기능
    public void deleteByUserId(int id, User user) {
        Reply reply = replyJpaRepository.findById(id)
                        .orElseThrow(() -> {
                           throw new Exception400("댓글을 찾을 수 없습니다.");
                        });

        if (reply.getUser().getId() == user.getId()) {
            replyJpaRepository.deleteById(id);
        }
    }
}