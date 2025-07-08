package com.tenco.web.community;

import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.reply.Reply;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityJpaRepository communityJpaRepository;

    // 게시글 목록 전체 조회
    public Page<Community> findAll(Pageable pageable) {
        Page<Community> jobSeekerList = communityJpaRepository.findAll(pageable);
        return jobSeekerList;
    }

    // 특정 게시글 목록 조회
    public List<Community> findByUserId(int id) {
        List<Community> jobSeekerList = communityJpaRepository.findByUserId(id);
        return jobSeekerList;
    }

    public Community findByIdWithReplies(int id, User sessionUser) {
        // 1. 게시글 조회
        Community community = communityJpaRepository.findByIdJoinUser(id);
        if (community == null) {
            throw new Exception404("게시글을 찾을 수 없습니다.");
        }

        if (sessionUser != null) {
            boolean isCommunityOwner = community.isOwner(sessionUser.getId());
            community.setCommunityOwner(isCommunityOwner);
        }

        List<Reply> replies = community.getReplies();

        if (sessionUser != null) {
            replies.forEach(reply -> {
                boolean isReplyOwner = reply.isOwner(sessionUser.getId());
                reply.setReplyOwner(isReplyOwner);
            });
        }
        return community;
    }

    // 게시글 상세조회
    public Community findById(int id) {
        Community community = communityJpaRepository.findByJobSeekerList(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음: id =" + id));

        return community;
    }

}
