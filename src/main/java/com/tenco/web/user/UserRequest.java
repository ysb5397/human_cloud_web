package com.tenco.web.user;

import com.tenco.web.utis.Define;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
        private String repeatPW;
        private String email;
        private String address;

        // JoinDTO를 user object로 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .address(this.address)
                    .build();
        }

        // 회원가입시 유효성 검증 메서드
        public String validate() {
            if (username == null || username.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_USERNAME;
            }

            if (password == null || password.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_PASSWORD;
            }

            if (password.length() < 4) {
                return Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD;
            }

            if (repeatPW == null || repeatPW.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_REPEAT_PW;
            }

            if (!password.equals(repeatPW)) {
                return Define.ErrorMessage.NOT_MATCH_REPEAT_PW;
            }

            if (email == null || email.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_EMAIL;
            }

            if (!email.contains("@")) {
                return Define.ErrorMessage.ILLEGAL_FORMAT_EMAIL;
            }

            if (address == null || address.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_ADDRESS;
            }
            return null;
        }
    }

    // 로그인용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String username;
        private String password;

        // 유효성 검사
        public void validate(Model model) {
            String errMsg = null;

            if (username == null || username.trim().isEmpty()) {
                errMsg = Define.ErrorMessage.REQUIRED_USERNAME;
            }

            if (password == null || password.trim().isEmpty()) {
                errMsg = Define.ErrorMessage.REQUIRED_PASSWORD;
            }

            model.addAttribute("errMsg", errMsg);
        }
    }

    @Data
    public static class UpdateDTO {
        private String email;
        private String password;
        private String address;

        public String validate() {
            if (!email.contains("@")) {
                return Define.ErrorMessage.ILLEGAL_FORMAT_EMAIL;
            }

            if (password == null || password.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_PASSWORD;
            }

            if (password.length() < 4) {
                return Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD;
            }

            if (address == null || address.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_ADDRESS;
            }
            return null;
        }
    }
}