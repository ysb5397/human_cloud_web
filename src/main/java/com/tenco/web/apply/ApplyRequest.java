package com.tenco.web.apply;

import lombok.Data;

public class ApplyRequest {

    @Data
    public static class SaveDTO {

        private int resumeId;
        private int announceId;
    }
}
