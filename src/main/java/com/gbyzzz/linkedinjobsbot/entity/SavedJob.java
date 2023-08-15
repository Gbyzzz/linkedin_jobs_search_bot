package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.type.PGSavedJobReplyState;
import com.gbyzzz.linkedinjobsbot.entity.type.PGUserProfileBotState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "saved_jobs")
public class SavedJob {

    @Id
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "reply_state")
    @Type(PGSavedJobReplyState.class)
    @Enumerated(EnumType.STRING)
    private ReplyState replyState;

    @Column(name = "date_applied")
    private Date dateApplied;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "users_jobs", joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "user_chat_id"))
    private Set<UserProfile> userProfile;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "searches_jobs", joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "search_params_id"))
    private Set<SearchParams> searchParams;



    public enum ReplyState {
        NEW_JOB,
        APPLIED,
        INTERVIEWING_IN_PROGRESS,
        REJECTED,
        DELETED
    }
}
