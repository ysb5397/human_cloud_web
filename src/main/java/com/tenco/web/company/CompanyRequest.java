package com.tenco.web.company;

import com.tenco.web.utis.Define;
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
        private String repeatPW;
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
        public String validate() {
            if (companyName == null || companyName.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_COMPANYNAME;
            }

            if (password == null || password.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_PASSWORD;
            }

            if(password.length() < 4){
                return Define.ErrorMessage.UNDER_FOUR_LENGTH_PASSWORD;
            }

            if(repeatPW == null || repeatPW.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_REPEAT_PW;
            }

            if(!password.equals(repeatPW)) {
                return Define.ErrorMessage.NOT_MATCH_REPEAT_PW;
            }

            if (address == null || address.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_ADDRESS;
            }

            if (businessRegistrationNumber == null || businessRegistrationNumber.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_BUSINESSREGISTRATION_NO;
            }

            if(email == null || email.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_EMAIL;
            }

            if (!email.contains("@")) {
                return Define.ErrorMessage.ILLEGAL_FORMAT_EMAIL;
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
        private String businessRegistrationNumber;
        private String password;

        // 유효성 검사
        public String validate() {
            if (businessRegistrationNumber == null || businessRegistrationNumber.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_BUSINESSREGISTRATION_NO;
            }

            if (password == null || password.trim().isEmpty()) {
                return Define.ErrorMessage.REQUIRED_PASSWORD;
            }
            return null;
        }
    }

}