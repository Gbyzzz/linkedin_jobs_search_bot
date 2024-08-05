package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    //    private int totalResults = 0;
    private final JobsRepository jobsRepository;
    //    private CopyOnWriteArraySet<String> results = new CopyOnWriteArraySet<>();
//    private final ObjectMapper mapper;
//    private String location;
//    private Long searchParamId;
//    private List<String> newJobs = new ArrayList<>();
    private final SearchParamsService searchParamsService;
    //
    private final SavedJobService savedJobService;
    private final MessageService messageService;
    private final LinkedInJobsBot linkedInJobsBot;
    private final PaginationKeyboard paginationKeyboard;


    //
//
//    public JobServiceImpl(JobsRepository jobsRepository, SearchParamsService searchParamsService, SavedJobService savedJobService) {
//        this.jobsRepository = jobsRepository;
//        this.searchParamsService = searchParamsService;
//        this.savedJobService = savedJobService;
////        this.mapper = new ObjectMapper();
//    }

    //
    @Override
    @KafkaListener(
            topics = "${application.kafka.topic.to_check_if_new}",
            groupId = "groupId",
            containerFactory = "kafkaListenerContainerFactory")
    public void checkIfNew(SearchParamsTimeRangeDTO searchParamsDTO) {
        System.out.println("Search id: " + searchParamsDTO.id());
        String include = MessageText.INCLUDE_REGEX_START + String.join(MessageText.REGEX_SEPARATOR,
                processKeywords(searchParamsDTO.filterParams().getIncludeWordsInDescription())) +
                MessageText.INCLUDE_REGEX_END;

        String exclude = MessageText.EXCLUDE_REGEX_START + String.join(MessageText.REGEX_SEPARATOR,
                searchParamsDTO.filterParams().getExcludeWordsFromTitle()) + MessageText.EXCLUDE_REGEX_END;
        Set<String> foundJobs = jobsRepository.findJobsIncludingAndExcludingWords(include, exclude,
                searchParamsDTO.id()).stream().map(job -> job.getId().toString()).collect(Collectors.toSet());
        System.out.println("After filtering:" + foundJobs.size());
        Set<String> savedJobs = savedJobService.getJobsByUserId(searchParamsDTO.userId()).stream().map(job ->
                job.getJobId().toString()).collect(Collectors.toSet());
        Set<String> toSave = new HashSet<>(foundJobs);
        toSave.removeAll(savedJobs);
        if (toSave.size() > 0) {
            List<String> jobDTO = new ArrayList<>(toSave);
            List<SavedJob> jobsToSave = getNewJobsToSave(jobDTO, searchParamsService.findById(searchParamsDTO.id()));
            savedJobService.saveAll(jobsToSave);
            List<SavedJob> jobs = savedJobService.getNewJobsByUserId(searchParamsDTO.userId());
            SendMessage sendMessage = messageService.getNewJobByUserId(searchParamsDTO.userId(),
                    MessageText.NEW + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.FIRST +
                    MessageText.BUTTON_VALUE_SEPARATOR + MessageText.ALL);
//            SendMessage sendMessage = new SendMessage(searchParamsDTO.userId().toString(),MessageText.makeNewJobsReply(0, jobs));
            //generate and send message to user
            linkedInJobsBot.sendMessage(sendMessage);
        }

//        if (!jobs.isEmpty()) {
//            List<SavedJob> jobsToSave = getNewJobsToSave(jobs, searchParams);
//            savedJobService.saveAll(jobsToSave);
//            System.out.println(jobsToSave.size());
//            return !jobsToSave.isEmpty();
//        }
    }

    //
//    public boolean makeScan(SearchParams searchParams, Long timePostedRange) {
//        List<String> foundIds = new ArrayList<>(results);
//        System.out.println(results);
//        System.out.println(totalResults);
//        return filterResults(searchParams, foundIds);
//    }
//
//    private void getTotalResults() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode jsonNode = mapper.readTree(makeRequest(new URL((urlBuilder + MessageText.ZERO)
//                .replaceAll(MessageText.COUNT_100, MessageText.COUNT_0))));
//        totalResults = jsonNode.get(MessageText.PAGING).get(MessageText.TOTAL).asInt();
//    }
//
    private String[] processKeywords(String[] keywords) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);


        for (int i = 0; i < keywords.length; i++) {
            Matcher m = p.matcher(keywords[i]);
            if (m.find()) {
                char[] characters = keywords[i].toCharArray();
                StringBuilder stringBuilder = new StringBuilder(MessageText.ALL_WORD_REGEX);
                for (char character : characters) {
                    if (p.matcher(String.valueOf(character)).find()) {
                        stringBuilder.append(MessageText.OPPOSITE_SLASH).append(character);
                    } else {
                        stringBuilder.append(character);
                    }
                }
                stringBuilder.append(MessageText.ALL_WORD_REGEX);
                keywords[i] = stringBuilder.toString();
            }
        }
        return keywords;
    }

    //
//    private void getJobIds(String in) throws JsonProcessingException {
//
//        JsonNode jsonNode = mapper.readTree(in);
//
//        if (jsonNode.get(MessageText.JSON_NODE_METADATA)
//                .has(MessageText.JSON_NODE_JOB_CARD_PREFETCH_QUERIES)) {
//            String[] node = mapper.convertValue(jsonNode
//                    .findPath(MessageText.JSON_NODE_PREFETCH_JOB_POSTING_CARD_URNS), String[].class);
//            results.addAll(Arrays.stream(node).map(
//                            (el) -> el.replaceAll(MessageText.NON_NUMERIC, MessageText.EMPTY))
//                    .toList());
//        }
//    }
//
//    private String makeRequest(URL url) throws IOException {
//        BufferedReader in;
//        StringBuilder content = new StringBuilder();
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestProperty(MessageText.COOKIE, COOKIE);
//        con.setRequestProperty(MessageText.CSRF_TOKEN, CSRF_TOKEN);
//        con.setRequestMethod(MessageText.GET);
//        while (content.isEmpty()) {
//            in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//        }
//        return content.toString();
//    }
//
    private List<SavedJob> getNewJobsToSave(List<String> jobs, SearchParams searchParams) {
        List<SavedJob> newJobs = new ArrayList<>();
        for (String newJob : jobs) {
            if (!savedJobService.existsJobByIdAndUserId(searchParams.getId(), searchParams.getUserProfile().getChatId())) {
                newJobs.add(new SavedJob(null, Long.parseLong(newJob), SavedJob.ReplyState.NEW_JOB,
                        null, searchParams.getUserProfile(), new HashSet<>() {{
                    add(searchParams);
                }}));
            }

        }
        return newJobs;
    }
}