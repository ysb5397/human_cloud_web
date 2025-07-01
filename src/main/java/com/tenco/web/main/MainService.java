package com.tenco.web.main;

import com.tenco.web.announce.Announce;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    private final MainJpaRepository mainJpaRepository;

    public List<Announce> findAll() {
        log.info("메인 페이지 불러오는 중");
        List<Announce> announceList = mainJpaRepository.findAll();

        log.info("메인 페이지 불러오기 완료");
        return announceList;
    }
}
