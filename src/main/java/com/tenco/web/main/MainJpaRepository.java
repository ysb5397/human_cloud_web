package com.tenco.web.main;

import com.tenco.web.announce.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MainJpaRepository extends JpaRepository<Announce, Integer> {

    @Query("SELECT a FROM Announce a JOIN FETCH a.company c WHERE a.id = :id")
    Optional<Announce> findByJobList(@Param("id") Long id);

}
