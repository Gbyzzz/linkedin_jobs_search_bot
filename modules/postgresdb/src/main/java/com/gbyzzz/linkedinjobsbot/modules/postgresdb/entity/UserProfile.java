package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.type.PGUserProfileBotState;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.type.PGUserProfileUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private Long chatId;
    @Column(name = "username")
    private String username;
    @Column(name = "bot_state")
    @Type(PGUserProfileBotState.class)
    @Enumerated(EnumType.STRING)
    private BotState botState;
    @Column(name = "registered_at")
    private Date registeredAt;
    private String password;
    @Type(PGUserProfileUserRole.class)
    private UserRole userRole;
    private String userPic;

    public enum UserRole {
        ADMIN,
        USER
    }

    public enum BotState {
        NA,
        ADD_KEYWORDS,
        ADD_LOCATION,
        ADD_EXPERIENCE,
        ADD_JOB_TYPE,
        ADD_WORKPLACE,
        ADD_FILTERS,
        ADD_FILTER_INCLUDE,
        ADD_FILTER_EXCLUDE,
        ADD_FILTER_JOB_TYPE,
        ADD_FILTER_WORKPLACE,
        NEW,
        APPLIED,
        SEARCHES
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserProfile that = (UserProfile) o;
        return getChatId() != null && Objects.equals(getChatId(), that.getChatId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}

