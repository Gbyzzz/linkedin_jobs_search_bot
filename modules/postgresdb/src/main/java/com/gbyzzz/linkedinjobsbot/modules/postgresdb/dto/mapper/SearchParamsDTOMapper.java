package com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper;


import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsDTO;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchParamsDTOMapper {
    SearchParamsDTO toDTO(SearchParams searchParams);
    SearchParams toEntity(SearchParamsDTO searchParams);
}
