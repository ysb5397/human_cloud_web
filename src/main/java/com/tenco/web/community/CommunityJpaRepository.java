package com.tenco.web.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommunityJpaRepository extends JpaRepository<Community, Integer> {

    @Query("select c from Community c join fetch c.user u where u.id = :id")
    List<Community> findByUserId(@Param("id") int id);

    @Query("select c from Community c join fetch c.user u where c.id = :id")
    Optional<Community> findByJobSeekerList(@Param("id") int id);

    @Query("select c from Community c join fetch c.user u order by c.id desc ")
    Page<Community> findAll(Pageable pageable);

    @Query("select c from Community c join fetch c.user u where c.id = :id ")
    Community findByIdJoinUser(@Param("id") int id);

}
