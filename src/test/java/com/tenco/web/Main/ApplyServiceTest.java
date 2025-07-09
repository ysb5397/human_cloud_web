//package com.tenco.web.apply;
//
//import com.tenco.web.announce.Announce;
//import com.tenco.web.announce.AnnounceJpaRepository;
//import com.tenco.web.company.Company;
//import com.tenco.web.company.CompanyJpaRepository;
//import com.tenco.web.resume.Resume;
//import com.tenco.web.resume.ResumeJpaRepository;
//import com.tenco.web.user.User;
//import com.tenco.web.user.UserJpaRepository;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//@Transactional
//public class ApplyServiceTest {
//
//    @Autowired
//    private ApplyService applyService;
//
//    @Autowired
//    private ApplyJpaRepository applyJpaRepository;
//
//    @Autowired
//    private UserJpaRepository userJpaRepository;
//
//    @Autowired
//    private ResumeJpaRepository resumeJpaRepository;
//
//    @Autowired
//    private AnnounceJpaRepository announceJpaRepository;
//
//    @Autowired
//    private CompanyJpaRepository companyJpaRepository;
//
//
//    private int userId;
//    private int resumeId;
//    private int announceId;
//
//    @BeforeEach
//    public void setup() {
//        // User 저장
//        User user = new User(1, "tester", "1234", "tester@naver.com", "010-1111-2222", null, null);
//        userJpaRepository.save(user);
//        userId = user.getId();
//
//        // 2. 회사 저장
//        Company company = new Company(); // 생성자나 빌더로 채워야 함
//        company.setCompanyName("테스트회사");
//        company.setAddress("서울");
//        companyJpaRepository.save(company);
//
//        // Resume 저장
//        Resume resume = new Resume(2, user, "성실합니다", "경력사항", "자기소개", true, null);
//        resumeJpaRepository.save(resume);
//        resumeId = resume.getId();
//
//        // Announce 저장
//        Announce announce = new Announce("채용합니다", "내용");
//        announce.setCompany(company);
//        announceJpaRepository.save(announce);
//        announceId = announce.getId();
//    }
//
//    @Test
//    public void 이력서_제출_테스트() {
//        // when
//        applyService.submitResume(ApplyRequest.SaveDTO);
//
//        // then
//        Apply savedApply = applyJpaRepository.findAll().get(0);
//        Assertions.assertThat(savedApply.getUser().getId()).isEqualTo(userId);
//        Assertions.assertThat(savedApply.getResume().getId()).isEqualTo(resumeId);
//        Assertions.assertThat(savedApply.getAnnounce().getId()).isEqualTo(announceId);
//    }
//}
