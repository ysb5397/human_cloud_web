package com.tenco.web.resume;

import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private static final Logger log = LoggerFactory.getLogger(ResumeService.class);

    private final ResumeJpaRepository resumeJpaRepository;

    // 이력서 저장 기능
    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO, User sessionuser) {
        log.info("이력서 저장 서비스 처리 시작 - 유저 id {}", saveDTO.getUserId());

        Resume resume = saveDTO.toEntity(sessionuser);

        log.info("Jpa 전");
        resumeJpaRepository.save(resume);
        log.info("Jpa 후");
    }

    /**
     * 이력서 목록 조회
     */
    public List<Resume> findAll() {
        List<Resume> resumeList = resumeJpaRepository.findAll();
        return resumeList;
    }

}
