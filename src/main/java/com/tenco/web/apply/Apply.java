package com.tenco.web.apply;

import com.tenco.web.announce.Announce;
import com.tenco.web.resume.Resume;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "apply_tb")
@Data
@NoArgsConstructor
@Builder
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announce_id")
    private Announce announce;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Apply(int id, User user, Resume resume, Announce announce, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.resume = resume;
        this.announce = announce;
        this.createdAt = createdAt;
    }

    public String getApplyDate() {
        return DateUtil.timestampFormat(createdAt);
    }
}
