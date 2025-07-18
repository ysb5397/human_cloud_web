package com.tenco.web.main;

import com.tenco.web.announce.Announce;
import com.tenco.web.announce.AnnounceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    private final MainJpaRepository mainJpaRepository;
    private final AnnounceService announceService;

    public List<Announce> findAll() {
        log.info("메인 페이지 불러오는 중");
        List<Announce> announceList = mainJpaRepository.findAll();

        log.info("메인 페이지 불러오기 완료");
        return announceList;
    }

    public List<Announce> findAllList() {
        List<Announce> announceList = mainJpaRepository.findAll();
        return announceList;
    }

    public Announce findById(Long id) {
        Announce announce = mainJpaRepository.findByJobList(id)
                .orElseThrow(() -> new IllegalArgumentException("공고 없음: id=" + id));;
        return announce;
    }

    public Page<Announce> findAll(Pageable pageable) {
        return announceService.findAll(pageable);
    }
}
