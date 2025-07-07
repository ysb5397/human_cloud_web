package com.tenco.web.announce;

import com.tenco.web.company.Company;
import com.tenco.web.utis.Define;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnnounceRequest {

    @Data
    public static class SaveJobDTO {

        @NotBlank(message = Define.ErrorMessage.REQUIRED_TITLE)
        private String title;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION)
        private String content;

        @NotBlank(message = "근무지역을 입력해주세요")
        private String workLocation;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private Timestamp endDate;

        private String endDateString;

        @Builder
        public Announce toEntity(Company sessionCompany) {
            return Announce.builder()
                    .title(this.title)
                    .content(this.content)
                    .workLocation(this.workLocation)
                    .company(sessionCompany)
                    .endDate(this.endDate)
                    .build();
        }


    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateDTO {
        private String title;
        private String content;
        private String workLocation;
        private Timestamp endDate;
        private String endDateString;

    }

}
