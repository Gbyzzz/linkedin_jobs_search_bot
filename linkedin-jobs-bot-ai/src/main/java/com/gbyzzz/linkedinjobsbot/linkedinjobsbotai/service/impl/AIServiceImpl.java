package com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.impl;

import com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.AIService;

import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AIServiceImpl implements AIService {

    @Qualifier("chatClientGemma")
    private final ChatClient chatClientGemma;

    @Qualifier("chatClientMistral")
    private final ChatClient chatClientMistral;

    @Qualifier("chatClientLlama")
    private final ChatClient chatClientLlama;
    private final SavedJobService savedJobService;
    private final JobService jobService;

    @KafkaListener(
            topics = "${application.kafka.topic.to_grade}",
            groupId = "groupId",
            containerFactory = "kafkaListenerContainerFactory")
    public void processJobs(Long userId) {
        List<ChatClient> chatClients = Arrays.asList(chatClientGemma, chatClientMistral,  chatClientLlama);
        List<SavedJob> savedJobs = savedJobService.getAppliedJobsByUserIdWhereResultIsNull(userId);
        for (SavedJob savedJob : savedJobs) {
            Optional<Job> job = jobService.getJob(savedJob.getJobId());
            if (job.isPresent()) {
                List<Integer> results = new ArrayList<>(101);
                String request = MessageText.PROMPT1 + MessageText.CV + MessageText.PROMPT2 +
                        job.get().getDescription();
                for(ChatClient client : chatClients) {
                    for (int i = 0; i < 101; i++) {
                        String reply = client.prompt()
                                .user(request).call().content();
                        if(reply != null) {
                            System.out.println(reply);
                            results.add(Integer.parseInt(reply.trim()));
                        }
                    }
                    if(client.equals(chatClientGemma)) {
                        savedJob.setGemmaGrade(getAvgGrade(results));
                    } else if(client.equals(chatClientMistral)) {
                        savedJob.setMistralGrade(getAvgGrade(results));
                    } else {
                        savedJob.setLlamaGrade(getAvgGrade(results));
                    }
                }


            } else {
                savedJob.setMistralGrade(0);
                savedJob.setGemmaGrade(0);
                savedJob.setLlamaGrade(0);
            }
            savedJobService.saveJob(savedJob);
        }
    }

    private int getAvgGrade(List<Integer> results) {
        results.sort(Comparator.naturalOrder());
        int median = results.get(50);
        int sum = 0;
        Iterator<Integer> iterator = results.iterator();
        while (iterator.hasNext()) {
            Integer result = iterator.next();
            if (Math.abs(result - median) < 20) {
                sum += result;
            } else {
                iterator.remove();
            }
        }
        return sum / results.size();
    }
}
