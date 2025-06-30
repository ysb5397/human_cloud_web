package com.tenco.web.company;

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
        private String companyName;
        private String password;
        private String address;
        private String businessRegistrationNumber;
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

        // 회원가입시 유효성 검증 메서드
        public void validate() {
            if (companyName == null || companyName.trim().isEmpty()) {
                throw new IllegalArgumentException("회사 명은 필수입니다");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("주소를 입력해주세요.");
            }

            if (businessRegistrationNumber == null || businessRegistrationNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("사업자번호는 필수입니다.");
            }

            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }

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
                throw new IllegalArgumentException("사업자번호는 필수입니다.");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
        }
    }

}