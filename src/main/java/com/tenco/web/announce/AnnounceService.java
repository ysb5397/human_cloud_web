package com.tenco.web.announce;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web.company.Company;
import com.tenco.web.company.CompanyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnnounceService {

    private static final Logger log = LoggerFactory.getLogger(AnnounceService.class);
    private final AnnounceJpaRepository announceJpaRepository;

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




}
