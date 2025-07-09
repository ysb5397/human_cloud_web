package com.tenco.web.announce;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tenco.web._core.common.CareerType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnounceJpaRepositoryImpl implements AnnounceRepositoryCustom {
    private final JPAQueryFactory queryFactory; // 2단계에서 등록한 빈을 주입받음
    private final Logger log = LoggerFactory.getLogger(AnnounceJpaRepositoryImpl.class);

    @Override
    public Page<Announce> search(AnnounceRequest.SearchDTO condition, Pageable pageable) {
        QAnnounce announce = QAnnounce.announce;
        // QAnnounceSkillTag skillTag = QAnnounceSkillTag.announceSkillTag; // 스킬 태그 조인을 위한 Q-Type

        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 조건
        if (condition.hasKeyword()) {
            builder.and(announce.title.contains(condition.getKeyword()));
        }

        // 경력 조건
        if (condition.hasCareer()) {
            try {
                // 1. String을 CareerType Enum으로 변환
                CareerType careerType = CareerType.valueOf(condition.getCareer().toUpperCase());

                // 2. 변환된 Enum 객체로 비교
                builder.and(announce.careerType.eq(careerType));

            } catch (IllegalArgumentException e) {
                // 잘못된 career 값이 들어올 경우 예외 처리 (선택 사항)
                log.error("Invalid career type: {}", condition.getCareer());
            }
        }

        // 스킬 태그 조건 (List 처리)
        if (condition.hasSkillTags()) {
            // announce.skillTagList는 Announce 엔티티에 정의된 List<AnnounceSkillTag> 필드여야 함
            // any()는 컬렉션의 요소 중 하나라도 조건을 만족하면 참을 반환
            builder.and(announce.announceSkillTags.any().skillTag.skillTagName.in(condition.getSkillTag()));
        }

        List<Announce> content = queryFactory
                .selectFrom(announce)
                // .leftJoin(announce.skillTagList, skillTag).fetchJoin() // 필요 시 조인 추가
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(announce.count())
                .from(announce)
                .where(builder)
                .fetchOne();

        // ... total 카운트 조회 및 Page 객체 반환 ...
        return new PageImpl<>(content, pageable, total);
    }
}
