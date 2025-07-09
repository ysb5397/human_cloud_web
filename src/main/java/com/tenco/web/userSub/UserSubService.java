package com.tenco.web.userSub;

import com.tenco.web._core.errors.exception.Exception400;
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
    private final UserSub userSub;

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

    public List<Company> getSubscribedCompanies(int userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });
        return  userSubJpaRepository.findByuUser(userId)
                .stream()
                .map(UserSub::getCompany)
                .toList();

    }



}
