package com.tenco.web.company;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyJpaRepository companyJpaRepository;

    /**
     * 회원 가입 처리
     *
     * @param joinDTO
     * @return Company
     */
    @Transactional // 메서드 레벨에서 쓰기 전용 트랜잭션 활성화
    public Company join(CompanyRequest.JoinDTO joinDTO) {
        // 1. 로그 기록
        log.info("회원가입 서비스 시작");

        // 2. 회사명 중복 체크(굳이 User 객체를 받을 필요가 없으므로 ifPresent 메서드가 더 적절하다.)
        companyJpaRepository.findByCompanyName(joinDTO.getCompanyName())
                .ifPresent(company1 -> {
                    throw new Exception400("이미 존재하는 회사 명입니다.");
                });


        log.info("회원가입 서비스 완료");
        return companyJpaRepository.save(joinDTO.toEntity());
    }

    /**
     * 로그인 처리
     *
     * @param loginDTO
     * @return Company
     */
    public Company login(CompanyRequest.LoginDTO loginDTO) {
        return companyJpaRepository
                .findByBusinessRegistrationNumberAndPassword(loginDTO.getBusinessRegistrationNumber(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new Exception400("사업자 번호 또는 비밀번호가 일치하지 않습니다.");
                });
    }

    /**
     * 회사 정보 조회
     *
     * @param id
     * @return company
     */
    public Company findById(int id) {
        return companyJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("회사 조회 실패 - ID : {}", id);
            return new Exception404("회사를 찾을 수 없습니다.");
        });
    }
}