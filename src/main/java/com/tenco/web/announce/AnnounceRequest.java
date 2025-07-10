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
import java.util.List;

public class AnnounceRequest {

    @Data
    @Builder
    public static class SaveJobDTO {

        @NotBlank(message = Define.ErrorMessage.REQUIRED_TITLE)
        private String title;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION)
        private String content;

        @NotBlank(message = "근무지역을 입력해주세요")
        private String workLocation;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime endDate;

        public Announce toEntity(Company sessionCompany) {
            return Announce.builder()
                    .title(this.title)
                    .content(this.content)
                    .workLocation(this.workLocation)
                    .company(sessionCompany)
                    .endDate(Timestamp.valueOf(this.endDate))
                    .build();
        }


    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateDTO {
        @NotBlank(message = Define.ErrorMessage.REQUIRED_TITLE)
        private String title;

        @NotBlank(message = Define.ErrorMessage.REQUIRED_SELF_INTRODUCTION)
        private String content;

        @NotBlank(message = "근무지역을 입력해주세요")
        private String workLocation;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime endDate;

        public Announce toEntity(Company sessionCompany) {
            return Announce.builder()
                    .title(this.title)
                    .content(this.content)
                    .workLocation(this.workLocation)
                    .company(sessionCompany)
                    .endDate(Timestamp.valueOf(this.endDate))
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchDTO {
        private String keyword;
        private String career;
        private List<String> skillTag;

        // --- Helper Methods ---
        public boolean hasKeyword() {
            return keyword != null && !keyword.trim().isEmpty();
        }
        public boolean hasCareer() {
            return career != null && !career.trim().isEmpty();
        }
        public boolean hasSkillTags() {
            return skillTag != null && !skillTag.isEmpty();
        }
    }

    @Data
    @AllArgsConstructor
    public static class FilterOptionDTO {
        private String name;    // 옵션의 이름 (예: "경력", "JAVA")
        private boolean checked; // 현재 선택되었는지 여부
    }
}
