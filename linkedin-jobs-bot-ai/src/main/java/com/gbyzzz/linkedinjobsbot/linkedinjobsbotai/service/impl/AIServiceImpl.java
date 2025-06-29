package com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.impl;

import com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.AIService;
//import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
//import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AIServiceImpl implements AIService {

    MistralAiChatModel chatModel;

//    @Override
//    public void gradeJob(Job job, UserProfile userProfile) {
//        ChatResponse response = chatModel.call(
//                new Prompt(
//                        "Generate the names of 5 famous pirates.",
//                        MistralAiChatOptions.builder()
//                                .model(MistralAiApi.ChatModel.LARGE.getValue())
//                                .temperature(0.0)
//                                .build()
//                ));
//        System.out.println(response);
//    }

    public void test() {
        ChatResponse response = chatModel.call(
                new Prompt(
                        "Generate the names of 5 famous pirates.",
                        MistralAiChatOptions.builder()
                                .model(MistralAiApi.ChatModel.LARGE.getValue())
                                .temperature(0.0)
                                .build()
                ));
        System.out.println(response);
    }
}
