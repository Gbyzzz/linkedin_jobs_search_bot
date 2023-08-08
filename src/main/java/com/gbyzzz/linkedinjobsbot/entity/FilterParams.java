package com.gbyzzz.linkedinjobsbot.entity;

import com.gbyzzz.linkedinjobsbot.entity.converter.KeywordConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "filter_params")
public class FilterParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "include")
    @Convert(converter = KeywordConverter.class)
    private String[] include;

    @OneToOne
    @JoinColumn(name = "search_id")
    private SearchParams searchParams;

    @EqualsAndHashCode.Include
    @Column(name = "exclude")
    @Convert(converter = KeywordConverter.class)
    private String[] exclude;

    @Override
    public String toString() {
        return "FilterParams{" +
                "id=" + id +
                ", include=" + Arrays.toString(include) +
                ", exclude=" + Arrays.toString(exclude) +
                '}';
    }
}
