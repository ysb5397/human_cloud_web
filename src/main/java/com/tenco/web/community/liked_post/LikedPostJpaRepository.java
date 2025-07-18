package com.tenco.web.community.liked_post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikedPostJpaRepository extends JpaRepository<LikedPost, Integer> {

    @Query("select l from LikedPost l where l.community.id = :communityId and l.user.id = :userId")
    LikedPost findByUserIdAndCommunityId(@Param("communityId") int communityId, @Param("userId") int userId);

    @Query("select l from LikedPost l where l.user.id = :userId")
    List<LikedPost> findByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    void deleteByUserIdAndCommunityId(int id, int communityId);
}
