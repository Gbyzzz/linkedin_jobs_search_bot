package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "filter_params")
public class FilterParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "include")
    @Convert(converter = KeywordConverter.class)
    private String[] include;

    @OneToOne
    @JoinColumn(name = "search_id")
    private SearchParams searchParams;

    @Column(name = "exclude")
    @Convert(converter = KeywordConverter.class)
    private String[] exclude;
}
