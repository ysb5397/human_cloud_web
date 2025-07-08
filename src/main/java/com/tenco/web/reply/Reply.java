package com.tenco.web.reply;

import com.tenco.web.community.Community;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "reply_tb")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToStringExclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    @ToStringExclude
    private Community community;

    private String comment;

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient
    public boolean replyOwner;

    @Builder
    public Reply(int id, String comment, User user, Community community, Timestamp createdAt) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.community = community;
        this.createdAt = createdAt;
    }

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }

    public boolean isOwner(int id) {
        return this.user.getId() == id;
    }
}
