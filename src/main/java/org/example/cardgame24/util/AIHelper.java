package org.example.cardgame24.util;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import java.util.Arrays;

public class AIHelper {

    interface Assistant {
        String chat(String message);
    }

    GoogleAiGeminiChatModel model;

    public AIHelper() {
        model = GoogleAiGeminiChatModel.builder()
                .apiKey("AIzaSyAczcBM0RRFvqSwBr7JI-FJnCg98_OsJn0")
                .modelName("gemini-2.5-flash") // Supports latest models
                .temperature(0.2)
                .build();
    }

    public String getSolution(Integer[] nums){
        try {
            String response = model.chat("""
                What is an equation that equals 24 just using these numbers? Only can use each digit once. just give me the equation, don't say anything else.
                """ + Arrays.toString(nums));
            return response.replaceAll(" ","");
        }catch (Exception e){
            //
        }
        return null;
    }
}
