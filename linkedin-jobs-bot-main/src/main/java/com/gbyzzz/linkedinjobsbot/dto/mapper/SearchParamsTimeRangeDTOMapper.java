package com.gbyzzz.linkedinjobsbot.dto.mapper;

import com.gbyzzz.linkedinjobsbot.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchParamsTimeRangeDTOMapper {

    @Mapping(source = "timePeriod", target = "timePostedRange")
    @Mapping(source = "searchParams.userProfile.chatId", target = "userId")
    SearchParamsTimeRangeDTO toDTO(SearchParams searchParams, Long timePeriod);
}
