package com.tenco.web.community;

import com.tenco.web.reply.Reply;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Data
@Table(name = "community_tb")
@Entity
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

    @Builder
    public Community(int id, String title, String content, Integer interestCount, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.interestCount = interestCount;
        this.user = user;
        this.createdAt = createdAt;
    }

    public boolean isOwner(int checkUserId){
        log.info("게시글 소유자 확인 요청 - 작성자 {}", checkUserId);
        return this.user.getId() == checkUserId;
    }

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }


    // @OrderBy("id DESC")
    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "community", cascade = CascadeType.REMOVE)
    // List<Reply> replies = new ArrayList<>();
}
