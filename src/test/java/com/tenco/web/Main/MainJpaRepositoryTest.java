package com.tenco.web.Main;

import com.tenco.web.announce.Announce;
import com.tenco.web.main.MainJpaRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class MainJpaRepositoryTest {

    private EntityManager entityManager;

    @Autowired
    private MainJpaRepository mainJpaRepository;

    @Test
    public void findAll_test() {
        List<Announce> announceList = mainJpaRepository.findAll();
        Assertions.assertThat(announceList.size()).isEqualTo(5);
    }


}
