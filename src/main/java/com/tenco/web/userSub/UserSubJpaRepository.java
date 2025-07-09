package com.tenco.web.userSub;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSubJpaRepository extends JpaRepository<UserSub, Integer> {

    @Query("select s from UserSub s join fetch s.user u where u.id = :id")
    List<UserSub> findByuUser(@Param("id") int id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserSub u WHERE u.user = :user AND u.company = :company")
    boolean existsByUserAndCompany(@Param("user") User user, @Param("company") Company company);
}
