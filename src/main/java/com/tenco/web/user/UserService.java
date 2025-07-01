package com.tenco.web.user;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web._core.errors.exception.LoginException;
import com.tenco.web.utis.Define;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJpaRepository userJpaRepository;

    /**
     * 회원 가입 처리
     *
     * @param joinDTO
     * @return User
     */
    @Transactional // 메서드 레벨에서 쓰기 전용 트랜잭션 활성화
    public User join(UserRequest.JoinDTO joinDTO) {
        // 1. 로그 기록
        log.info("회원가입 서비스 시작");

        // 2. 사용자명 중복 체크(굳이 User 객체를 받을 필요가 없으므로 ifPresent 메서드가 더 적절하다.)
        userJpaRepository.findByUsername(joinDTO.getUsername())
                .ifPresent(user1 -> {
                    throw new Exception400("이미 존재하는 사용자 명입니다.");
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
        Optional<User> user = userJpaRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return userJpaRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new LoginException(Define.ErrorMessage.NOT_MATCH_USERNAME_OR_PASSWORD);
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

    // 회원 정보 수정 요청(더티 체킹)
    @Transactional
    public User updateById(int id, UserRequest.UpdateDTO updateDTO) {
        User user = findById(id);

        // TODO 추가
//        user.update(updateDTO);

        user.setPassword(updateDTO.getPassword());
        return user; // 세션 동기화 때문에 반환 필요
    }
}