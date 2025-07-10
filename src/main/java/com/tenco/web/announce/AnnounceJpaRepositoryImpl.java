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
    private final JPAQueryFactory queryFactory;
    private final Logger log = LoggerFactory.getLogger(AnnounceJpaRepositoryImpl.class);

    @Override
    public Page<Announce> search(AnnounceRequest.SearchDTO condition, Pageable pageable) {
        QAnnounce announce = QAnnounce.announce;

        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 조건
        if (condition.hasKeyword()) {
            builder.and(announce.title.contains(condition.getKeyword()));
        }

        // 경력 조건
        if (condition.hasCareer()) {
            try {
                CareerType careerType = CareerType.valueOf(condition.getCareer().toUpperCase());

                builder.and(announce.careerType.eq(careerType));

            } catch (IllegalArgumentException e) {
                log.error("Invalid career type: {}", condition.getCareer());
            }
        }

        if (condition.hasSkillTags()) {
            builder.and(announce.announceSkillTags.any().skillTag.skillTagName.in(condition.getSkillTag()));
        }

        List<Announce> content = queryFactory
                .selectFrom(announce)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(announce.count())
                .from(announce)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
