package com.tenco.web.resume;

import com.tenco.web.user.User;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

        @NotBlank(message = Define.ErrorMessage.REQUIRED_EMAIL)
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        private String email;

        private String address;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_PORTFOLIO_URL)
        private String portfolioUrl;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION)
        private String selfIntroduction;

        private Boolean isPublic;

        private List<String> skillTags;
        private String careerType;

        public Resume toEntity(User sessionuser) {
            return Resume.builder()
                    .title(this.title)
                    .email(this.email)
                    .address(this.address)
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
        private String email;
        private String address;
        private String portfolioUrl;
        private String selfIntroduction;
        private List<String> skillTags;
        private String careerType;
    }
}
