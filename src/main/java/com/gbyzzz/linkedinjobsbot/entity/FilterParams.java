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
    private Long id;

    @Column(name = "include")
    @Convert(converter = KeywordConverter.class)
    private String[] include;

    @Column(name = "exclude")
    @Convert(converter = KeywordConverter.class)
    private String[] exclude;

    @Column(name = "type")
    @Convert(converter = KeywordConverter.class)
    private String[] type;

    @Column(name = "workplace")
    @Convert(converter = KeywordConverter.class)
    private String[]workplace;
}
