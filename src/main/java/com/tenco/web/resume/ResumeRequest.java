package com.tenco.web.resume;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResumeRequest {


    // 이력서 저장 DTO
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

    // 이력서 수정 DTO
    @Data
    public static class UpdateDTO {

        private String title;
        private String portfolioUrl;
        private String selfIntroduction;

        public String validate() {
            if(title == null || title.trim().isEmpty()){
                return Define.ErrorMessage.REQUIRED_TITLE;
            }
            if(portfolioUrl == null || portfolioUrl.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_PORTFOLIO_URL;
            }
            if(selfIntroduction == null || selfIntroduction.trim().isEmpty()){
                return Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION;
            }
            return null;
        }
    }
}
