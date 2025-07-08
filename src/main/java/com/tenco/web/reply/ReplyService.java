package com.tenco.web.reply;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final 키워드를 가진 멤버를 초기화 해
@Service // IoC 대상
public class ReplyService {

    private static final Logger log = LoggerFactory.getLogger(ReplyService.class);
    // DI 처리
    private final ReplyJpaRepository replyJpaRepository;

    // 댓글 저장 기능
    // 서비스 계층, Repository 계층에 메서드 이름 (같이 하거나, 다르게 정의)


    // 댓글 삭제 기능

}