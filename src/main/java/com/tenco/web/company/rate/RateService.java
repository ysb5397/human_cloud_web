package com.tenco.web.company.rate;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.company.Company;
import com.tenco.web.company.CompanyJpaRepository;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RateService {

    private final RateJpaRepository rateJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    public void save(RateRequest.SaveDTO saveDTO, User sessionUser, int companyId) {
        Company company = companyJpaRepository.findById(companyId)
                        .orElseThrow(() -> {
                            throw new Exception404("해당하는 회사를 찾을 수 없습니다.");
                        });

        Rate rate = rateJpaRepository.findByUserAndCompany(sessionUser.getId(), companyId);

        if (rate != null) {
            throw new Exception400("한 회사에 평가를 한 번만 남길 수 있습니다.");
        }

        rate = saveDTO.toEntity(company, sessionUser);
        rateJpaRepository.save(rate);
    }
}
