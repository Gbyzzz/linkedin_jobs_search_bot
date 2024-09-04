package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.dto.mapper.SearchParamsTimeRangeDTOMapper;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.KafkaService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    @Value("${time.period}")
    private Long timePeriod;

    private final SearchParamsService searchParamsService;
    private final KafkaService kafkaService;
    private final UserProfileService userProfileService;
    private final SearchParamsTimeRangeDTOMapper searchParamsTimeRangeDTOMapper;


    @Scheduled(cron = "0 0/${scan.period} * * * ?")
    public void makeScan() {
        System.out.println("Scheduled");
        List<UserProfile> userProfiles = userProfileService.getAll();
        System.out.println(userProfiles);

        for (UserProfile user : userProfiles) {
            System.out.println("search for - " + user.getUsername() + ", chat id: " + user.getChatId());
            List<SearchParams> searchParams =
                    searchParamsService.findAllByUserId(user.getChatId());
            if (!searchParams.isEmpty()) {
                for (SearchParams searchParam : searchParams) {
                    kafkaService.sendMessage("to_search",
                            searchParamsTimeRangeDTOMapper.toDTO(searchParam, timePeriod));
                }
            }
        }
    }
}
