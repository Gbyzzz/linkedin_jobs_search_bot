package com.gbyzzz.jobscanner.dto;

import java.util.Map;

public record SearchParamsTimeRangeDTO(
        Long id,
        String[] keywords,
        String location,
        Long userId,
        FilterParamsDTO filterParams,
        Map<String, String> searchFilters,
        Long timePostedRange) {
}