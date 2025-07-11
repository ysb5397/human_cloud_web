package com.tenco.web.tags.announce_tag;

import lombok.Data;

@Data
public class AnnounceSkillTagRequest {

    public static class CheckDTO {
        private int id;
        private String skillTagName;
        private boolean isChecked; // 'checked' 상태를 나타내는 필드

        public CheckDTO(Integer id, String skillTagName, boolean isChecked) {
            this.id = id;
            this.skillTagName = skillTagName;
            this.isChecked = isChecked;
        }
    }
}
