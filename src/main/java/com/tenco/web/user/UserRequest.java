package com.tenco.web.user;

import com.tenco.web.utis.Define;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
        @NotBlank(message = Define.ErrorMessage.REQUIRED_USERNAME)
        private String username;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_PASSWORD)
        @Size(min = 4, message = Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD)
        private String password;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_REPEAT_PW)
        private String repeatPW;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_EMAIL)
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        private String email;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_ADDRESS)
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
    }

    // 로그인용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Data
    public static class NaverUserDTO {
        private String name;
        private String email;
    }

    // 수정 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateDTO {
        @NotBlank(message = Define.ErrorMessage.REQUIRED_USERNAME)
        private String username;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_PASSWORD)
        @Size(min = 4, message = Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD)
        private String password;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_REPEAT_PW)
        private String repeatPW;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_EMAIL)
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        private String email;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_ADDRESS)
        private String address;

        // UpdateDTO를 user object로 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .address(this.address)
                    .build();
        }
    }
}