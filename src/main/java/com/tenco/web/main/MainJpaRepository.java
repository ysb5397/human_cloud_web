package com.tenco.web.main;

import com.tenco.web.announce.Announce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainJpaRepository extends JpaRepository<Announce, Integer> {

}
