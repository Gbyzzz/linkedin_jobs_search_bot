package com.gbyzzz.linkedinjobsbot.modules.dto.dto;

import java.util.Map;

public record SearchParamsDTO(
        Long id,
        String[] keywords,
        String location,
//        UserProfileDTO userProfile,
        Map<String, String> searchFilters,
        FilterParamsDTO filterParams
) {
}
