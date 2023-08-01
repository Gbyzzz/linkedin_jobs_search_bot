package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.type.PGSavedJobReplyState;
import com.gbyzzz.linkedinjobsbot.entity.type.PGUserProfileBotState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_user")
public class SavedJob {

    @Id
    @Column(name = "job_id")
    private Long jobId;

    @ManyToOne
    @JoinColumn(name = "user_chat_id")
    private UserProfile userProfile;
    @Column(name = "applied")
    private boolean applied;
    @Column(name = "reply_state")
    @Type(PGSavedJobReplyState.class)
    @Enumerated(EnumType.STRING)
    private ReplyState replyState;

    public enum ReplyState {
        APPLIED,
        INTERVIEWING_IN_PROGRESS,
        REJECTED,
        DELETED
    }
}
