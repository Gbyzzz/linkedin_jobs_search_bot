package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "search_params",uniqueConstraints = @UniqueConstraint(columnNames =
        {"keywords", "location", "user_chat_id"}))
public class SearchParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keywords")
    @Convert(converter = KeywordConverter.class)
    private String[] keywords;

    @Column(name = "location")
    private String location;

    @OneToOne()
    @JoinColumn(name = "user_chat_id")
    private UserProfile userProfile;


    @ElementCollection
    @CollectionTable(name = "search_filters", joinColumns = @JoinColumn(name = "search_id"))
    @MapKeyColumn(name = "filter_name")
    @Column(name = "value")
    private Map<String, String> searchFilters;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchParams")
    @JoinTable( name = "filter_params", joinColumns = @JoinColumn(name = "search_id"))
    private FilterParams filterParams;
}
