package com.tenco.web.community;

import com.tenco.web.community.liked_post.LikedPost;
import com.tenco.web.reply.Reply;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Data
@Table(name = "community_tb")
@Entity
@ToString(exclude = "replies")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private Integer interestCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    @Formula("(SELECT count(*) FROM reply_tb r WHERE r.community_id = id)")
    private int replyCount;

    @Transient
    private boolean communityOwner;

    private String category;

    @Transient
    private boolean isLiked;

    @Builder
    public Community(int id, String title, String content, User user, Timestamp createdAt, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.category = category;
    }

    public boolean isOwner(int checkUserId) {
        log.info("게시글 소유자 확인 요청 - 작성자 {}", checkUserId);
        return this.user.getId() == checkUserId;
    }

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }


    @OrderBy("id DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "community", cascade = CascadeType.REMOVE)
    List<Reply> replies = new ArrayList<>();

    @OrderBy("id DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "community", cascade = CascadeType.REMOVE)
    @ToStringExclude
    List<LikedPost> likes = new ArrayList<>();
}
