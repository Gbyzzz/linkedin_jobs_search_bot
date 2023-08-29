package com.gbyzzz.linkedinjobsbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "filter_params")
public class FilterParams {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_params_id")
    private Long id;

    @ToString.Include
    @Column(name = "include_words_desc")
    @Convert(converter = KeywordConverter.class)
    private String[] includeWordsInDescription;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "search_params_id")
    private SearchParams searchParams;

    @ToString.Include
    @Column(name = "exclude_words_title")
    @Convert(converter = KeywordConverter.class)
    private String[] excludeWordsFromTitle;

}
