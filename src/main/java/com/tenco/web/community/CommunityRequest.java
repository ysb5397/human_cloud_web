package com.tenco.web.community;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommunityRequest {

    // 커뮤니티 글 저장 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveDTO {

        @NotBlank(message = Define.ErrorMessage.REQUIRED_TITLE)
        private String title;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_CONTENT)
        private String content;

        private String category;

        public Community toEntity(User sessionUser) {
            return Community.builder()
                    .title(this.title)
                    .content(this.content)
                    .category(this.category)
                    .user(sessionUser)
                    .build();
        }
    }
}
