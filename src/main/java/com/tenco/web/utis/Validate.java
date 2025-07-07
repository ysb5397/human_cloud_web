package com.tenco.web.utis;

import com.tenco.web.company.CompanyRequest;
import com.tenco.web.user.UserRequest;
import org.springframework.validation.BindingResult;

public class Validate {
    public static class UserValidate {
        public static void checkJoinDTO(UserRequest.JoinDTO joinDTO, BindingResult result) {
            if (!joinDTO.getPassword().equals(joinDTO.getRepeatPW())) {
                result.rejectValue("repeatPW", "password.mismatch", Define.ErrorMessage.NOT_MATCH_REPEAT_PW);
            }
        }

        public static void checkUpdateDTO(UserRequest.UpdateDTO updateDTO, BindingResult result) {
            if(!updateDTO.getPassword().equals(updateDTO.getRepeatPW())) {
                result.rejectValue("repeatPW", "password.mismatch", Define.ErrorMessage.NOT_MATCH_REPEAT_PW);
            }
        }
    }


    public static class CompanyValidate {
        public static void checkJoinDTO(CompanyRequest.JoinDTO joinDTO, BindingResult result) {
            if (!joinDTO.getPassword().equals(joinDTO.getRepeatPW())) {
                result.rejectValue("repeatPW", "password.mismatch", Define.ErrorMessage.NOT_MATCH_REPEAT_PW);
            }
        }
    }
}
