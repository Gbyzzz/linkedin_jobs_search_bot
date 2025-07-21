package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.modules.kafka.service.KafkaService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper.SearchParamsTimeRangeDTOMapper;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
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
        System.out.println("Scheduled scan system");
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

    @Scheduled(cron = "0 0/1 * * * ?")
    public void makeGrade() {
        System.out.println("Scheduled grading system");
        kafkaService.sendMessage("to_grade",null);
    }
}