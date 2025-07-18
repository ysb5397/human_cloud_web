package com.tenco.web.community.liked_post;

import com.tenco.web.community.Community;
import com.tenco.web.user.User;
import lombok.Data;

public class LikePostRequest {

    @Data
    public static class SaveDTO {
        private int interestCount;

        public LikedPost toEntity(Community community, int interestCount, User user) {
            return LikedPost.builder()
                    .interestCount(interestCount)
                    .community(community)
                    .user(user)
                    .build();
        }
    }
}
