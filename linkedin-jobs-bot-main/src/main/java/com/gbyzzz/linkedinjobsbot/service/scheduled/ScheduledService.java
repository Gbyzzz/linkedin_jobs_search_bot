package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.ListOfJobsCommand;
import com.gbyzzz.linkedinjobsbot.dto.mapper.SearchParamsTimeRangeDTOMapper;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.entity.converter.SendToEditMessageConverter;
import com.gbyzzz.linkedinjobsbot.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduledService {


    private final SearchParamsService searchParamsService;
    private final KafkaService kafkaService;
    private final UserProfileService userProfileService;
    private final SearchParamsTimeRangeDTOMapper searchParamsTimeRangeDTOMapper;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void makeScan() throws IOException {
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
                            searchParamsTimeRangeDTOMapper.toDTO(searchParam, 14400L));
                }
            }
        }
    }


}
