package com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper;

import com.gbyzzz.linkedinjobsbot.modules.dto.dto.UserProfileDTO;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileDTOMapper {
    @Mapping(source = "userProfile.botState", target = "botState")
    @Mapping(source = "userProfile.userRole", target = "userRole")
    UserProfileDTO toDTO(UserProfile userProfile);
    @Mapping(source = "userProfileDTO.botState", target = "botState")
    @Mapping(source = "userProfileDTO.userRole", target = "userRole")
    UserProfile toEntity(UserProfileDTO userProfileDTO);
}
