package com.tenco.web.userSub;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web.announce.Announce;
import com.tenco.web.announce.AnnounceJpaRepository;
import com.tenco.web.company.Company;
import com.tenco.web.company.CompanyJpaRepository;
import com.tenco.web.user.User;
import com.tenco.web.user.UserJpaRepository;
import com.tenco.web.utis.Define;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSubService {

    private static final Logger log = LoggerFactory.getLogger(UserSubService.class);
    // DI 처리
    private final UserSubJpaRepository userSubJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final AnnounceJpaRepository announceJpaRepository;

    // 구독 저장 기능
    public  void Usersubscribe(int userId, int companyId) {
        User user = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });
        Company company = companyJpaRepository.findById(companyId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });

        if (userSubJpaRepository.existsByUserAndCompany(user, company)) {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        }

        UserSub subscription = UserSub.builder()
                .user(user)
                .company(company)
                .build();

        userSubJpaRepository.save(subscription);

    }

    // 구독한 기업 리스트 목록
    public List<Company> getSubscribedCompanies(int userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });
        return  userSubJpaRepository.findByuUser(userId)
                .stream()
                .map(UserSub::getCompany)
                .toList();

    }

    // 구독목록 중 특정기업을 눌렀을 때 해당 기업의 공고 전체가 뜨는 기능
    public List<Announce> getCompanyHirelist(int userId, int companyId) {
        // 1. 인증체크 <- 인터셉터에서처리
        // 2. 권한체크는 필요없음.
        // 3. 공고가 있는지 없는지? 머스테치에서 다 보여짐.
        User user = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new Exception403(Define.ErrorMessage.ERROR_403);
        });

        return announceJpaRepository.findAllByCompanyId(companyId);

    }


}
