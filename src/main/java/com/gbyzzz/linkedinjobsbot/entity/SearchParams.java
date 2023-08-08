package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "search_params",uniqueConstraints = @UniqueConstraint(columnNames =
        {"keywords", "location", "user_chat_id"}))
public class SearchParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "keywords")
    @Convert(converter = KeywordConverter.class)
    private String[] keywords;

    @EqualsAndHashCode.Include
    @Column(name = "location")
    private String location;

    @EqualsAndHashCode.Include
    @OneToOne()
    @JoinColumn(name = "user_chat_id")
    private UserProfile userProfile;


    @EqualsAndHashCode.Include
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "search_filters", joinColumns = @JoinColumn(name = "search_id"))
    @MapKeyColumn(name = "filter_name")
    @Column(name = "value")
    private Map<String, String> searchFilters;

    @EqualsAndHashCode.Include
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchParams", fetch = FetchType.EAGER)
    @JoinTable( name = "filter_params", joinColumns = @JoinColumn(name = "search_id"))
    private FilterParams filterParams;

}
