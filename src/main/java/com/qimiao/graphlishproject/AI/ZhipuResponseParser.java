package com.qimiao.graphlishproject.AI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qimiao.graphlishproject.DTO.Tech.ZhipuResponse;

public class ZhipuResponseParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String extractContent(String json) {
        try {
            ZhipuResponse response =
                    objectMapper.readValue(json, ZhipuResponse.class);

            if (response.choices == null || response.choices.isEmpty()) {
                throw new RuntimeException("No choices in response");
            }

            ZhipuResponse.Message message =
                    response.choices.getFirst().message;

            if (message == null || message.content == null) {
                throw new RuntimeException("No message content in response");
            }

            return message.content;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Zhipu AI response", e);
        }


    }

}
