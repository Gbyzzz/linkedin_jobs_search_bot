package com.gbyzzz.linkedinjobsbotentityservice.dto.mapper;

import com.gbyzzz.linkedinjobsbotentityservice.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.linkedinjobsbotentityservice.entity.SearchParams;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring")
public interface SearchParamsTimeRangeDTOMapper {

    @ValueMapping(source = "timePeriod", target = "timePostedRange")
    @ValueMapping(source = "searchParams.userProfile.chatId", target = "userId")
    SearchParamsTimeRangeDTO toDTO(SearchParams searchParams, Long timePeriod);
}
