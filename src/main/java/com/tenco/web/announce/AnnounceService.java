package com.tenco.web.announce;

import com.tenco.web._core.common.CareerType;
import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception401;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.company.Company;
import com.tenco.web.tags.SkillTag;
import com.tenco.web.tags.SkillTagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnnounceService {

    private static final Logger log = LoggerFactory.getLogger(AnnounceService.class);
    private final AnnounceJpaRepository announceJpaRepository;
    private final SkillTagService skillTagService;

    // 공고 정보 등록 기능
    @Transactional
    public void save(AnnounceRequest.SaveJobDTO saveJobDTO, Company sessionCompany) {
//        log.info("공고 정보 저장 서비스 처리 시작 - 기업 id {}", saveJobDTO.getCompany().getId());

        Announce announce = new Announce();
        announce.setTitle(saveJobDTO.getTitle());
        announce.setContent(saveJobDTO.getContent());
        announce.setWorkLocation(saveJobDTO.getWorkLocation());
        announce.setEndDate(Timestamp.valueOf(saveJobDTO.getEndDate()));
        announce.setCompany(sessionCompany);

        if (saveJobDTO.getEndDate().isBefore(announce.getStartDateTime())) {
            throw new Exception400("마감일은 현재 시간보다 이후여야 합니다.");
        }
        
        log.info("Jpa 전");
        announceJpaRepository.save(announce);
        log.info("Jpa 후");

    }


    // 상세보기에서 게시글 삭제
    @Transactional
    public void deleteById(int id, Company sessionCompany){

        log.info("게시글 삭제 서비스 시작 - 게시글 ID {}", id);
        if (sessionCompany == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        Announce announce = announceJpaRepository.findById(id).orElseThrow(() -> {
           throw new Exception404("삭제할 게시글이 없습니다");
        });
        if(!announce.isCOwner(sessionCompany.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        announceJpaRepository.deleteById(id);
        log.info("게시글 삭제 완료 - 게시글 ID {}", id);
    }

    // 게시글 권한 확인 서비스

    public void checkCoBoardOwner(int announceId, int companyId) {
        Announce announce = announceJpaRepository.findById(announceId).orElseThrow(() -> {
            throw new Exception400("수정할 공고가 없습니다.");
        });
        if(!announce.isCOwner(companyId)) {
            throw new Exception403("본인이 작성한 공고만 수정할 수 있습니다.");
        }
    }

// 마이페이지에 내 공고목록 조회하기
    /**
     * 특정 회사가 등록한 모든 공고 목록을 조회합니다. (register-list 페이지용)
     * @param companyId 조회할 회사의 ID
     * @return 해당 회사의 공고 목록
     */
    public List<Announce> findMyAnnounceList(int companyId) {
        // Repository에 새로 추가한 메서드를 호출하여 DB에서 직접 목록을 가져옵니다.
        List<Announce> announceList = announceJpaRepository.findAllByCompanyId(companyId);
        return announceList;
    }

    // 상세보기에서 게시글 수정
    // 업데이트
    @Transactional
    public Announce UpdateById(int id, AnnounceRequest.UpdateDTO reqDTO, Company sessionCompany) {
       log.info("공고 수정 서비스 시작 - 공고 ID {}", id);
       Announce announce = announceJpaRepository.findById(id).orElseThrow(() -> {
           log.warn("공고 조회 실패 - ID {}", id);
           return new Exception404("해당 공고가 존재하지 않습니다");
       });

       if(!announce.isCOwner(sessionCompany.getId())) {
           throw new Exception403("본인 공고만 수정 가능합니다.");
       }

        if (reqDTO.getEndDate().isBefore(announce.getStartDateTime())) {
            throw new Exception400("마감일은 현재 시간보다 이후여야 합니다.");
        }

        announce.setTitle(reqDTO.getTitle());
       announce.setContent(reqDTO.getContent());
       announce.setWorkLocation(reqDTO.getWorkLocation());
       announce.setEndDate(Timestamp.valueOf(reqDTO.getEndDate()));
       announce.setCompany(sessionCompany);


        log.info("공고 수정 완료 - 공고 ID {}, 공고 제목 {}", id, announce.getTitle());
        return announce;
    }

    // 게시글 단건 조회하기
    @Transactional(readOnly = true)
    public Announce findAnnounceWithCompanyById(int id) {
        return announceJpaRepository.findByIdJoinCompany(id)
                .orElseThrow(() -> new Exception404("해당 공고를 찾을 수 없습니다. ID: " + id));
    }


    public Page<Announce> findAnnounceWithKeyword(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return announceJpaRepository.findAll(pageable);
        } else {
            return announceJpaRepository.findAnnounceByKeyword(keyword, pageable);
        }
    }
    
    public Page<Announce> findAll(Pageable pageable) {
        return announceJpaRepository.findAll(pageable);
    }

    public Announce findById(int id) {
        Announce announce = announceJpaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new Exception404("공고글을 찾을 수 없습니다.");
                });

        return announce;
    }

    public Page<Announce> search(AnnounceRequest.SearchDTO condition, Pageable pageable) {
        return announceJpaRepository.search(condition, pageable);
    }

    public List<AnnounceRequest.FilterOptionDTO> getSkillTagOptions(AnnounceRequest.SearchDTO condition) {
        List<SkillTag> allSkills = skillTagService.findAll();
        List<String> selectedSkills = Optional.ofNullable(condition.getSkillTag()).orElse(Collections.emptyList());

        // ✅ 제네릭 헬퍼 메서드 호출
        return createTagOptions(
                allSkills,                  // 전체 아이템 목록
                selectedSkills,             // 선택된 아이템 이름 목록
                SkillTag::getSkillTagName,  // 아이템에서 이름(String)을 추출하는 방법
                AnnounceRequest.FilterOptionDTO::new // 이름(String)과 체크여부(boolean)로 DTO를 생성하는 방법
        );
    }

    public List<AnnounceRequest.FilterOptionDTO> getCareerOptions(AnnounceRequest.SearchDTO condition) {
        List<CareerType> allCareers = new ArrayList<>();

        for (CareerType c : CareerType.values()) {
            if (!c.equals(CareerType.무직)) {
                allCareers.add(c);
            }
        }
        String selectedCareer = condition.getCareer();

        // ✅ 제네릭 헬퍼 메서드 호출
        return createTagOptions(
                allCareers,
                Collections.singletonList(selectedCareer), // 단일 선택이므로 하나의 요소만 있는 리스트로 전달
                CareerType::name,
                AnnounceRequest.FilterOptionDTO::new
        );
    }

    private <T> List<AnnounceRequest.FilterOptionDTO> createTagOptions(
            List<T> allItems,
            Collection<String> selectedItems,
            Function<T, String> nameExtractor,
            BiFunction<String, Boolean, AnnounceRequest.FilterOptionDTO> dtoConstructor
    ) {
        return allItems.stream()
                .map(item -> {
                    String itemName = nameExtractor.apply(item);
                    boolean isChecked = selectedItems.contains(itemName);
                    return dtoConstructor.apply(itemName, isChecked);
                })
                .toList();
    }
}
