package com.tenco.web.utis;

public class Define {

    // 에러 메시지 담당하는 내부 클래스
    public static class ErrorMessage {

        // 공통
        // 에러 메시지
        public static final String ERROR_400 = "400 Bad Request Error";
        public static final String ERROR_401 = "401 Unauthorized Error";
        public static final String ERROR_403 = "403 Forbidden Error";
        public static final String ERROR_404 = "404 Not Found Error";
        public static final String ERROR_500 = "500 Internal Server Error";
        public static final String USER_LOGIN_ERROR = "유저 로그인 오류";
        public static final String COMPANY_LOGIN_ERROR = "회사 로그인 오류";

        // 기타 에러 메시지
        public static final String REQUIRED_PASSWORD = "비밀번호는 필수입니다.";
        public static final String UNDER_FOUR_LENGTH_PASSWORD = "비밀번호는 네 글자 이상으로 입력해주세요.";
        public static final String REQUIRED_REPEAT_PW = "비밀번호를 재입력 해주세요.";
        public static final String NOT_MATCH_REPEAT_PW = "재입력한 비밀번호가 일치하지 않습니다.";
        public static final String REQUIRED_EMAIL = "이메일은 필수입니다.";
        public static final String ILLEGAL_FORMAT_EMAIL = "이메일을 똑바로 입력해주세요.";
        public static final String REQUIRED_LOGIN = "로그인이 필요한 서비스입니다.";
        public static final String REQUIRED_CONTENT = "내용은 필수입니다.";

        // 유저
        public static final String REQUIRED_USERNAME = "이름은 필수입니다.";
        public static final String NOT_MATCH_USERNAME_OR_PASSWORD = "이름 또는 비밀번호가 일치하지 않습니다.";
        public static final String REQUIRED_ADDRESS = "주소는 필수입니다.";
        public static final String EXIST_USER = "이미 존재하는 유저입니다.";

        // 회사
        // 회사이름 사업자번호
        public static final String REQUIRED_COMPANYNAME = "회사명은 필수 입니다.";
        public static final String REQUIRED_BUSINESSREGISTRATION_NO = " 사업자 번호는 필수입니다.";
        public static final String NOT_MATCH_BUSINESSREGISTRATION_NO_OR_PASSWORD = "사업자 번호 또는 비밀번호가 일치하지 않습니다.";
        public static final String EXIST_COMPANY = "이미 존재하는 회사입니다.";

        // 이력서
        public static final String REQUIRED_TITLE = "제목은 필수입니다.";
        public static final String REQUIRED_PORTFOLIO_URL = "포트 폴리오는 필수입니다.";
        public static final String REQUIRED_SELF_INTRODUCTION = "자기소개는 필수입니다.";
    }

    // 기타 메시지 담당하는 내부 클래스
    public static class NormalMessage {
        public static final String NOT_EXIST_USER = "사용 가능한 이름입니다.";
        public static final String NOT_EXIST_COMPANY = "사용 가능한 회사명입니다.";
    }

    // 이외 하드코딩 방지용 내부 클래스
    public static class DefineMessage {
        public static final String SESSION_USER = "sessionUser";
        public static final String SESSION_COMPANY = "sessionCompany";
        public static final String JOIN_DTO = "joinDTO";
        public static final String LOGIN_DTO = "loginDTO";
        public static final String SAVE_DTO = "saveDTO";
        public static final String SAVE_JOB_DTO = "saveJobDTO";
        public static final String UPDATE_DTO = "updateDTO";
        public static final String MESSAGE_USERNAME_CHECK = "message.usernameCheck";
        public static final String MESSAGE_USERNAME = "message.username";
        public static final String MESSAGE_COMPANY_NAME_CHECK = "message.companyNameCheck";
        public static final String MESSAGE_COMPANY_NAME = "message.companyName";
    }
}
