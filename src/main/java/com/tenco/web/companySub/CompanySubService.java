package com.tenco.web.companySub;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web.announce.AnnounceJpaRepository;
import com.tenco.web.company.Company;
import com.tenco.web.company.CompanyJpaRepository;
import com.tenco.web.user.User;
import com.tenco.web.user.UserJpaRepository;
import com.tenco.web.utis.Define;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanySubService {

    private final CompanySubJpaRepository companySubJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AnnounceJpaRepository announceJpaRepository;

    // 구독 저장 기능
    public void companySubscribe(int companyId, int userId) {
        Company company = companyJpaRepository.findById(companyId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });
        User user = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });

        if(companySubJpaRepository.existsByCompanyAndUser(company, user)){
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        }
        CompanySub subscription = CompanySub.builder()
                    .company(company)
                    .user(user)
                    .build();

        companySubJpaRepository.save(subscription);
    }

    // 구독한 유저 리스트 목록
    public List<User> getSubscribedUsers(int companyId) {
        Company company = companyJpaRepository.findById(companyId).orElseThrow(() -> {
            throw new Exception400(Define.ErrorMessage.ERROR_400);
        });
        return companySubJpaRepository.findByCompany(companyId)
                .stream()
                .map(CompanySub::getUser)
                .toList();

    }

}
