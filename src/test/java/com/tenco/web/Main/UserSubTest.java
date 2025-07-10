//package com.tenco.web.main;
//
//import com.tenco.web.announce.Announce;
//import com.tenco.web.announce.AnnounceJpaRepository;
//import com.tenco.web.company.Company;
//import com.tenco.web.company.CompanyJpaRepository;
//import com.tenco.web.user.User;
//import com.tenco.web.user.UserJpaRepository;
//import com.tenco.web.userSub.UserSub;
//import com.tenco.web.userSub.UserSubJpaRepository;
//import com.tenco.web.userSub.UserSubService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//
//@SpringBootTest
//@Transactional
//public class UserSubTest {
//
//    @Autowired
//    private UserSubService userSubService;
//
//    @Autowired
//    private UserSubJpaRepository userSubJpaRepository;
//
//    @Autowired
//    private UserJpaRepository userJpaRepository;
//
//    @Autowired
//    private CompanyJpaRepository companyJpaRepository;
//
//    @Autowired
//    private AnnounceJpaRepository announceJpaRepository;
//
//    private int userId;
//    private int companyId;
//
//    @BeforeEach
//    public void setup() {
//        // User 저장
//        User user = new User(1, "tester", "1234", "tester@naver.com", "010-1111-2222", null, null);
//        userJpaRepository.save(user);
//        userId = user.getId();
//
//        // 회사 저장
//        Company company = new Company();
//        company.setCompanyName("테스트회사");
//        company.setAddress("서울");
//        companyJpaRepository.save(company);
//        companyId = company.getId();
//
//
//        announceJpaRepository.save(Announce.builder()
//                .title("공고1")
//                .company(company)
//                .build());
//
//        announceJpaRepository.save(Announce.builder()
//                .title("공고2")
//                .company(company)
//                .build());
//
//        userSubJpaRepository.save(UserSub.builder()
//                .user(user)
//                .company(company)
//                .build());
//
//
//    }
//
//    @Test
//    public void 구독_저장_테스트() {
//        //when
//        userSubService.Usersubscribe(userId, companyId);
//
//        // given
//        List<UserSub> userSubs = userSubJpaRepository.findByuUser(userId);
//
//        // then
//        Assertions.assertThat(userSubs.size()).isEqualTo(1);
//
//
//    }
//
//    @Test
//    public void 구독_목록_조회_테스트() {
//        // when
//        userSubService.Usersubscribe(userId, companyId);
//
//        // given
//        List<Company> subscribedCompanies = userSubService.getSubscribedCompanies(userId);
//
//        // then
//        Assertions.assertThat(subscribedCompanies.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void 구독한_회사_공고_리스트_목록_테스트() {
//        // when
//        // 현재 나의 구독한 회사 리스트
//        List<Company> subscribedCompanies = userSubService.getSubscribedCompanies(userId);
//        Assertions.assertThat(subscribedCompanies).hasSize(1);
//        Company targetCompany = subscribedCompanies.get(0);
//        Assertions.assertThat(targetCompany.getCompanyName()).isEqualTo("테스트회사");
//
//        // given
//        // 내가 구독한 회사목록 중 테스트회사를 클릭한다면
//
//        List<Announce> announces = userSubService.getCompanyHirelist(userId, targetCompany.getId());
//
//
//        // then
//
//        Assertions.assertThat(announces).hasSize(2);
//        Assertions.assertThat(announces.get(0).getCompany().getCompanyName()).isEqualTo("테스트회사");
//    }
//}
