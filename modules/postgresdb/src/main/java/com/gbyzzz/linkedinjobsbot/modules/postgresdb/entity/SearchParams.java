package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchParams")
    @JoinTable( name = "filter_params", joinColumns = @JoinColumn(name = "search_params_id"))
    private FilterParams filterParams;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "searches_jobs", joinColumns = @JoinColumn(name = "search_params_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<SavedJob> savedJobs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchParams that = (SearchParams) o;
        return Objects.deepEquals(keywords, that.keywords) &&
                Objects.equals(location, that.location) &&
                Objects.equals(userProfile, that.userProfile) &&
                Objects.equals(searchFilters, that.searchFilters) &&
                Objects.equals(filterParams, that.filterParams);
    }

    public SearchParams(UserProfile userProfile) {
        this.filterParams = new FilterParams();
        this.searchFilters = new HashMap<>();
        this.userProfile = userProfile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(keywords), location, userProfile, searchFilters, filterParams, savedJobs);
    }
}
