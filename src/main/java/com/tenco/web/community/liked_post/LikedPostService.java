package com.tenco.web.community.liked_post;

import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.community.Community;
import com.tenco.web.community.CommunityJpaRepository;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikedPostService {

    private final LikedPostJpaRepository likedPostJpaRepository;
    private final CommunityJpaRepository communityJpaRepository;

    @Transactional
    public void update(int communityId, Integer interestCount, User sessionUser) {
        Community community = communityJpaRepository.findById(communityId)
                .orElseThrow(() -> {
                    throw new Exception404("게시물이 없습니다.");
                });

        LikedPost likedPost = likedPostJpaRepository.findByUserIdAndCommunityId(communityId, sessionUser.getId());
        LikePostRequest.SaveDTO saveDTO = new LikePostRequest.SaveDTO();

        if (likedPost == null) {
            likedPostJpaRepository.save(saveDTO.toEntity(community, interestCount, sessionUser));
        } else {
            likedPostJpaRepository.deleteByUserIdAndCommunityId(sessionUser.getId(), communityId);
        }
    }

    public List<LikedPost> findByUserId(int userId) {
        return likedPostJpaRepository.findByUserId(userId);
    }
}
