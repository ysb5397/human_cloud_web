package com.tenco.web.company.rate;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RateRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SaveDTO {

        private int rating;
        private String comment;

        public Rate toEntity(Company company, User user){
            return Rate.builder()
                    .rating(this.rating)
                    .comment(this.comment)
                    .company(company)
                    .user(user)
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateDTO {

        private int rating;
        private String comment;
    }
}
