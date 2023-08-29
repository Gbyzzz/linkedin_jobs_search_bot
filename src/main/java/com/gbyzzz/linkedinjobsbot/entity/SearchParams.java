package com.gbyzzz.linkedinjobsbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import com.gbyzzz.linkedinjobsbot.entity.type.PGSearchParamsState;
import com.gbyzzz.linkedinjobsbot.entity.type.PGUserProfileBotState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "search_params",uniqueConstraints = @UniqueConstraint(columnNames =
        {"keywords", "location", "user_chat_id"}))
public class SearchParams {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_params_id")
    private Long id;

    @ToString.Include
    @Column(name = "keywords")
    @Convert(converter = KeywordConverter.class)
    private String[] keywords;

    @ToString.Include
    @Column(name = "location")
    private String location;

    @OneToOne()
    @JoinColumn(name = "user_chat_id")
    private UserProfile userProfile;


    @ToString.Include
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "search_filters", joinColumns = @JoinColumn(name = "search_params_id"))
    @MapKeyColumn(name = "filter_name")
    @Column(name = "value")
    private Map<String, String> searchFilters;

    @ToString.Include
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchParams", fetch = FetchType.EAGER)
    @JoinTable( name = "filter_params", joinColumns = @JoinColumn(name = "search_params_id"))
    private FilterParams filterParams;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "searches_jobs", joinColumns = @JoinColumn(name = "search_params_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<SavedJob> savedJobs;

    @ToString.Include
    @Column(name = "search_state")
    @Type(PGSearchParamsState.class)
    @Enumerated(EnumType.STRING)
    private SearchParams.SearchState searchState;

    public enum SearchState {
        NEW,
        SUBSCRIBED
    }
}
