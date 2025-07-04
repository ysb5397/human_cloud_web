package com.tenco.web.announce;

import com.sun.tools.javac.Main;
import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web.company.Company;
import com.tenco.web.company.CompanyJpaRepository;
import com.tenco.web.main.MainService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnnounceService {

    private static final Logger log = LoggerFactory.getLogger(AnnounceService.class);
    private final AnnounceJpaRepository announceJpaRepository;

    // 공고 정보 등록 기능
    @Transactional
    public void save(AnnounceRequest.SaveJobDTO saveJobDTO, Company sessionCompany) {
//        log.info("공고 정보 저장 서비스 처리 시작 - 기업 id {}", saveJobDTO.getCompany().getId());

        Announce announce = saveJobDTO.toEntity(sessionCompany);

        log.info("Jpa 전");
        announceJpaRepository.save(announce);
        log.info("Jpa 후");

    }



    // 상세보기에서 게시글 수정

    // 상세보기에서 게시글 삭제
    @Transactional
    public void deleteById(int id, Company sessionCompany){

        log.info("게시글 삭제 서비스 시작 - 게시글 ID {}", id);
        Announce announce = announceJpaRepository.findById(id).orElseThrow(() -> {
           throw new Exception400("삭제할 게시글이 없습니다");
        });
        if(!announce.isCOwner(sessionCompany.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        announceJpaRepository.deleteById(id);
        log.info("게시글 삭제 완료 - 게시글 ID {}", id);
    }

    // 게시글 권한 확인 서비스

    public void checkCoBoardOwner(int announceId, int companyId) {
        Announce announce = announceJpaRepository.findById(announceId).orElseThrow(() -> {
            throw new Exception400("본인 게시글만 수정할 수 있습니다.");
        });
    }

//
    /**
     * 특정 회사가 등록한 모든 공고 목록을 조회합니다. (register-list 페이지용)
     * @param companyId 조회할 회사의 ID
     * @return 해당 회사의 공고 목록
     */
    public List<Announce> findMyAnnounceList(int companyId) {
        // Repository에 새로 추가한 메서드를 호출하여 DB에서 직접 목록을 가져옵니다.
        List<Announce> announceList = announceJpaRepository.findAllByCompanyId(companyId);
        return announceList;
    }



}
