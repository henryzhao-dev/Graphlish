package com.qimiao.graphlishproject.AI;

import com.qimiao.graphlishproject.AI.Interface.AIProvider;
import com.qimiao.graphlishproject.Config.ZhipuAIProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class ZhipuAIProviderImpl implements AIProvider {

    private final HttpClient httpClient;
    private final ZhipuAIProperties zhipuAIProperties;


    public ZhipuAIProviderImpl(ZhipuAIProperties zhipuAIProperties) {
        this.zhipuAIProperties = zhipuAIProperties;
        this.httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.of(null))
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }


    //explain word
    @Override
    public String explainWord(String word) {

        String prompt = PromptTemplates.generateExplanation(word);

        String responseJson = callZhipuAI(prompt);

        return ZhipuResponseParser.extractContent(responseJson);


    }

    //explain word again
    @Override
    public String reExplain(String explanation) {

        String prompt = PromptTemplates.simplifyExplanation(explanation);

        String responseJson = callZhipuAI(prompt);

        return ZhipuResponseParser.extractContent(responseJson);


    }

    private String callZhipuAI(String prompt)  {

        String requestBody = """
                    {
                       "model": "glm-4",
                       "messages": [
                         {
                           "role": "user",
                           "content": "%s"
                         }
                       ],
                       "temperature": 0.5
                    }
                    """.formatted(escapeJson(prompt));

        int maxAttempts = 2;
        int attempt = 0;

        //retry at most once
        while (attempt < maxAttempts) {
            attempt++;

            try {
                String endpoint = zhipuAIProperties.getEndpoint();
                String apiKey = zhipuAIProperties.getApiKey();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endpoint))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + apiKey)
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                HttpResponse<String> response =
                        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException("Zhipu AI call failed: " + response.body());
                }
                return response.body(); //return directly if successful

            } catch (Exception e) {
                if (attempt >= maxAttempts) {
                throw new RuntimeException("Failed to call Zhipu AI", e);
            }
                System.out.println("Zhipu AI call failed, retrying... attempt=" + attempt);
            }

        }

        throw new RuntimeException("Zhipu AI call failed unexpectedly");

    }


    //process prompt format
    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
}
