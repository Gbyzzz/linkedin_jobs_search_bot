package com.gbyzzz.linkedinjobsbotentityservice.dto;

import lombok.*;

import java.util.Arrays;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FilterParamsDTO {
    private Long id;
    private String[] includeWordsInDescription;
//    @JsonIgnore
//    private SearchParamsDTO searchParams;
    private String[] excludeWordsFromTitle;

    @Override
    public String toString() {
        return "FilterParamsDTO{" +
                "id=" + id +
                ", includeWordsInDescription=" + Arrays.toString(includeWordsInDescription) +
                ", excludeWordsFromTitle=" + Arrays.toString(excludeWordsFromTitle) +
                '}';
    }
}
