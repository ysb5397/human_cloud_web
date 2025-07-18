package com.tenco.web.community.liked_post;

import com.tenco.web.community.Community;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "liked_post_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "community_id"})})
@ToString(exclude = {"user", "community"})
public class LikedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @ToStringExclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "community_id")
    @ToStringExclude
    private Community community;

    private int interestCount;

    @CreationTimestamp
    private Timestamp createdAt;

    public LikedPost(Community community, User sessionUser) {
        this.community = community;
        this.user = sessionUser;
    }

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }

    public boolean isOwner(int checkUserId) {
        return this.user.getId() == checkUserId;
    }
}