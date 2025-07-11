package com.tenco.web.user;

import com.tenco.web._core.errors.exception.Exception403;
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

    public User findByUpdateUsername(UserRequest.UpdateDTO updateDTO) {
        return userJpaRepository.findByUsername(updateDTO.getUsername()).orElse(null);
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
                .ifPresent(user -> {
                    throw new IllegalArgumentException(Define.ErrorMessage.EXIST_USER);
                });

        log.info("회원가입 서비스 완료");
        return userJpaRepository.save(joinDTO.toEntity());
    }

    public User login(UserRequest.LoginDTO loginDTO) {
        return userJpaRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    throw new UserLoginException(Define.ErrorMessage.NOT_MATCH_USERNAME_OR_PASSWORD);
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

    // 회원 소유자 확인( 수정 화면 요청)
    public void checkUserOwner(int userId, int id) {
        User user = findById(userId);
        if(!user.isOwner(id)) {
            throw new Exception403("본인 정보만 수정할 수 있습니다.");
        }
    }

    // 회원정보수정하기
    @Transactional
    public User updateById(int id, UserRequest.UpdateDTO updateDTO, User sessionUser) {
        log.info("회원 정보 수정 서비스 시작 - 회원 ID {}", id);
        User user = userJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("회원 조회 실패 - ID {}", id);
            return new Exception404("회원을 찾을 수 없습니다.");
        });

        if(!user.isOwner(sessionUser.getId())) {
            throw new Exception403("본인 정보만 수정 가능합니다.");
        }

        user.setUsername(updateDTO.getUsername());
        user.setEmail(updateDTO.getEmail());
        user.setPassword(updateDTO.getPassword());
        user.setAddress(updateDTO.getAddress());

        log.info("회원 정보 수정 완료 - 회원 ID {}, 회원 이메일 {} ",id, user.getEmail());
        return user;
    }

    public UserRequest.UpdateDTO getUserInfoById(int userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    return null;
                });

        return new UserRequest.UpdateDTO(
                user.getUsername(),
                "",
                "",
                user.getEmail(),
                user.getAddress()
        );
    }

    @Transactional
    public User findOrCreateUserByNaver(String email, String name) {
        User user = userJpaRepository.findByEmail(email);

        if (user == null) {
            // 2. 사용자가 없으면 새로 생성
            String username = (name != null && !name.trim().isEmpty()) ? name : email.split("@")[0];
            user = User.builder()
                    .email(email)
                    .username(username) // username을 네이버 이름으로 설정
                    // 소셜 로그인은 별도의 비밀번호가 필요 없으므로 임의의 값(UUID 등)을 저장하거나,
                    // DB 컬럼에 nullable=true 설정을 합니다.
                    .password("provided_by_naver")
                    .build();

            userJpaRepository.save(user); // DB에 저장
        }

        // 3. 사용자 정보 반환 (찾았거나, 새로 만들었거나)
        return user;
    }
}