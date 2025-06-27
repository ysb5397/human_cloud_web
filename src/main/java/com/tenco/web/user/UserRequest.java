package com.tenco.web.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
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
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명은 필수입니다");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }

            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("주소를 입력해주세요.");
            }
        }
    }

    // 로그인용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String email;
        private String password;

        // 유효성 검사
        public void validate() {
            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
        }
    }

    @Data
    public static class UpdateDTO {
        private String email;
        private String password;
        private String address;

        public void validate() {
            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }

            if (password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4글자 이상으로 입력해주세요.");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("주소를 입력해주세요.");
            }
        }
    }
}