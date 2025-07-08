package com.tenco.web.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityJpaRepository communityJpaRepository;

    // 게시글 목록 전체 조회
    public List<Community> findAll() {
        List<Community> jobSeekerList = communityJpaRepository.findAll();
        return jobSeekerList;
    }

    // 특정 게시글 목록 조회
    public List<Community> findByUserId(int id) {
        List<Community> jobSeekerList = communityJpaRepository.findByUserId(id);
        return jobSeekerList;
    }

    // 게시글 상세조회
    public Community findById(int id) {
        Community community = communityJpaRepository.findByJobSeekerList(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음: id =" + id));

        return community;
    }

}
