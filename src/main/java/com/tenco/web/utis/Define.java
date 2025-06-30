package com.tenco.web.utis;

public class Define {

    // 에러 메시지 담당하는 내부 클래스
    public static class ErrorMessage {
        // 공통
        public static final String REQUIRED_PASSWORD = "비밀번호는 필수입니다.";
        public static final String UNDER_FOUR_LENGTH_PASSWORD = "비밀번호는 네 글자 이상으로 입력해주세요.";
        public static final String REQUIRED_EMAIL = "이메일은 필수입니다.";
        public static final String ILLEGAL_FORMAT_EMAIL = "이메일을 똑바로 입력해주세요.";
        public static final String REQUIRED_ADDRESS = "주소는 필수입니다.";

        // 유저
        public static final String REQUIRED_USERNAME = "유저 이름은 필수입니다.";
        public static final String NOT_MATCH_USERNAME_OR_PASSWORD = "유저 이름 또는 비밀번호가 일치하지 않습니다.";
        public static final String REQUIRED_REPEAT_PW = "비밀번호를 재입력 해주세요.";
        public static final String NOT_MATCH_REPEAT_PW = "재입력한 비밀번호가 일치하지 않습니다.";

        // 회사

    }

    // 기타 메시지 담당하는 내부 클래스
    public static class NormalMessage {

    }

    // 이외 하드코딩 방지용 내부 클래스
    public static class DefineMessage {
        public static final String SESSION_USER = "sessionUser";
    }
}
