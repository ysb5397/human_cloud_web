package com.tenco.web.apply;

import com.tenco.web.announce.Announce;
import com.tenco.web.announce.AnnounceJpaRepository;
import com.tenco.web.resume.Resume;
import com.tenco.web.resume.ResumeJpaRepository;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final AnnounceJpaRepository announceJpaRepository;


    // 이력서 제출
    @Transactional
    public void submitResume(User user, int resumeId, int announceId){

        Resume resume = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("이력서가 없습니다."));
        Announce announce = announceJpaRepository.findById(announceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고입니다."));

        Apply apply = new ApplyRequest.SaveDTO().toEntity(user, resume, announce);
        applyJpaRepository.save(apply);
    }

    // 특정 유저가 제출한 지원 목록
    public List<Apply> findByUserId(int id) {
        List<Apply> applyList = applyJpaRepository.findByUserId(id);
        return applyList;
    }
}
