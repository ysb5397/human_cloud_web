package com.tenco.web.apply;

import com.tenco.web.announce.Announce;
import com.tenco.web.announce.AnnounceJpaRepository;
import com.tenco.web.resume.Resume;
import com.tenco.web.resume.ResumeJpaRepository;
import com.tenco.web.user.User;
import com.tenco.web.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final AnnounceJpaRepository announceJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public void submitResume(int userId, int resumeId, int announceId){
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Resume resume = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("이력서가 없습니다."));
        Announce announce = announceJpaRepository.findById(announceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고입니다."));

        Apply apply = Apply.builder()
                .user(user)
                .resume(resume)
                .announce(announce)
                .build();

        applyJpaRepository.save(apply);


    }
}
