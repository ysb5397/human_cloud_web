package com.tenco.web.user;

import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web._core.errors.exception.UserLoginException;
import com.tenco.web.utis.Define;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJpaRepository userJpaRepository;


    public User findByUsername(UserRequest.JoinDTO joinDTO) {
        return userJpaRepository.findByUsername(joinDTO.getUsername()).orElse(null);
    }

    /**
     * 회원 가입 처리
     *
     * @param joinDTO
     * @return User
     */
    @Transactional
    public User join(UserRequest.JoinDTO joinDTO) {
        log.info("회원가입 서비스 시작");
        userJpaRepository.findByUsername(joinDTO.getUsername())
                .ifPresent(user1 -> {
                    throw new Exception404("a");
                });

        log.info("회원가입 서비스 완료");
        return userJpaRepository.save(joinDTO.toEntity());
    }

    /**
     * 로그인 처리
     *
     * @param loginDTO
     * @return User
     */
    public User login(UserRequest.LoginDTO loginDTO) {
        return userJpaRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new UserLoginException(Define.ErrorMessage.NOT_MATCH_USERNAME_OR_PASSWORD);
                });
    }

    /**
     * 사용자 정보 조회
     *
     * @param id
     * @return User
     */
    public User findById(int id) {
        return userJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("사용자 조회 실패 - ID : {}", id);
            return new Exception404("사용자를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public User updateById(int id, UserRequest.UpdateDTO updateDTO) {
        User user = findById(id);

        user.setPassword(updateDTO.getPassword());
        return user;
    }
}