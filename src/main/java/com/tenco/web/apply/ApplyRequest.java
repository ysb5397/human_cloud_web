package com.tenco.web.apply;

import com.tenco.web.announce.Announce;
import com.tenco.web.resume.Resume;
import com.tenco.web.user.User;
import lombok.Data;

public class ApplyRequest {

    @Data
    public static class SaveDTO {

        private int userId;
        private int resumeId;
        private int announceId;

        public Apply toEntity(User user, Resume resume, Announce announce){
            return Apply.builder()
                    .user(user)
                    .resume(resume)
                    .announce(announce)
                    .build();
        }
    }
}
