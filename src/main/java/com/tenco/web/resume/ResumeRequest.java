package com.tenco.web.resume;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResumeRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveDTO{

        private Integer userId;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_TITLE)
        private String title;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_PORTFOLIO_URL)
        private String portfolioUrl;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION)
        private String selfIntroduction;

        private Boolean isPublic;

        public Resume toEntity(User sessionuser) {
            return Resume.builder()
                    .title(this.title)
                    .portfolioUrl(this.portfolioUrl)
                    .selfIntroduction(this.selfIntroduction)
                    .user(sessionuser)
                    .build();
        }
    }
}
