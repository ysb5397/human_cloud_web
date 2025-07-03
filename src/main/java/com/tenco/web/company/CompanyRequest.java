package com.tenco.web.company;

import com.tenco.web._core.errors.exception.CompanyLoginException;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CompanyRequest {


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO{
        @NotBlank(message = Define.ErrorMessage.REQUIRED_COMPANYNAME)
        private String companyName;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_PASSWORD)
        @Size(min = 4, message = Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD)
        private String password;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_REPEAT_PW)
        private String repeatPW;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_ADDRESS)
        private String address;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_BUSINESSREGISTRATION_NO)
        private String businessRegistrationNumber;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_EMAIL)
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        private String email;

        // JoinDTO를 company object로 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public Company toEntity(){
            return Company.builder()
                    .companyName(this.companyName)
                    .password(this.password)
                    .address(this.address)
                    .businessRegistrationNumber(this.businessRegistrationNumber)
                    .email(this.email)
                    .build();
        }
    }

    // 로그인용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String businessRegistrationNumber;
        private String password;

        // 유효성 검사
        public void validate() {
            if (businessRegistrationNumber == null || businessRegistrationNumber.trim().isEmpty()) {
                throw new CompanyLoginException(Define.ErrorMessage.REQUIRED_BUSINESSREGISTRATION_NO);
            }

            if (password == null || password.trim().isEmpty()) {
                throw new CompanyLoginException(Define.ErrorMessage.REQUIRED_PASSWORD);
            }
        }
    }
}