package com.tenco.web.company;

import com.tenco.web._core.errors.exception.CompanyLoginException;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.utis.Define;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        companyJpaRepository.findByCompanyName(joinDTO.getCompanyName());

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
                    throw new CompanyLoginException(Define.ErrorMessage.NOT_MATCH_BUSINESSREGISTRATION_NO_OR_PASSWORD);
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

    /**
     * 회사 리스트 조회
     */
    public List<Company> findAll() {
        List<Company> companyList = companyJpaRepository.findAll();
        return companyList;
    }

    public Company findByCompanyName(CompanyRequest.JoinDTO joinDTO) {
        Company company = companyJpaRepository.findByCompanyName(joinDTO.getCompanyName());
        return company;
    }

    public Company findByUpdateCompanyName(CompanyRequest.UpdateDTO updateDTO) {
        Company company = companyJpaRepository.findByCompanyName(updateDTO.getCompanyName());
        return company;
    }



    /**
     * 회사정보 수정
     */
    @Transactional
    public Company updateById(int id, CompanyRequest.UpdateDTO updateDTO, Company sessionCompany) {
        log.info("회사 정보 수정 서비스 시작 - 회사 ID {}", id);
        Company company = companyJpaRepository.findByCompanyName(updateDTO.getCompanyName());
        if (company == null) {
            log.warn("기업 조회 실패 - ID {}", id);
            throw new Exception404(Define.ErrorMessage.ERROR_404);
        }


        if (!company.isOwner(sessionCompany.getId())) {
            throw new Exception403(Define.ErrorMessage.ERROR_403);
        }

        company.setCompanyName(updateDTO.getCompanyName());
        company.setEmail(updateDTO.getEmail());
        company.setPassword(updateDTO.getPassword());
        company.setAddress(updateDTO.getAddress());

        log.info("회사 정보 수정 완료 - 회사 ID {}, 회사 이메일 {} ", id, company.getEmail());

        return company;

    }


}