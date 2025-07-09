package com.tenco.web.apply;

import com.tenco.web.announce.Announce;
import com.tenco.web.announce.AnnounceJpaRepository;
import com.tenco.web.resume.Resume;
import com.tenco.web.resume.ResumeJpaRepository;
import com.tenco.web.user.User;
import com.tenco.web.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final AnnounceJpaRepository announceJpaRepository;
    private final UserJpaRepository userJpaRepository;

    // 이력서 제출
    @Transactional
    public void submitResume(int id, ApplyRequest.SaveDTO saveDTO, User sessionUser){
        User user = userJpaRepository.findById(saveDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Resume resume = resumeJpaRepository.findById(saveDTO.getResumeId())
                .orElseThrow(() -> new IllegalArgumentException("이력서가 없습니다."));
        Announce announce = announceJpaRepository.findById(saveDTO.getAnnounceId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고입니다."));

        Apply apply = Apply.builder()
                .user(user)
                .resume(resume)
                .announce(announce)
                .build();

        applyJpaRepository.save(apply);


    }
}
