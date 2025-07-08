package com.tenco.web.reply;

import com.tenco.web.community.Community;
import com.tenco.web.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        private int communityId;

        @NotBlank(message = "댓글을 작성해주세요.")
        private String comment;

        public Reply toEntity(User sessionUser, Community community) {
            return Reply.builder()
                    .comment(this.comment.trim())
                    .user(sessionUser)
                    .community(community)
                    .build();
        }
    }

}
