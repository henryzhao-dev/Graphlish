package com.qimiao.graphlishproject.AI;

public class PromptTemplates {

    private PromptTemplates() {}


    //generate standard and structured explanations
    public static String generateExplanation(String word) {
        return """
        You are an English dictionary.

        Please explain the English word "%s" with the following rules:
        1. Give a clear and accurate definition in simple English.
        2. Provide ONE short example sentence.
        3. Do NOT explain in a casual way.
        4. Keep the explanation concise and suitable for storage.

        Output format:
        Explanation:
        <definition>

        Example:
        <example sentence>
        """.formatted(word);
    }


    //generate more accessible descriptions based on existing explanations
//    public static String simplifyExplanation(String originalExplanation) {
//        return """
//        You are teaching a beginner.
//
//        The following is a dictionary-style explanation:
//
//        %s
//
//        Please explain it again in a much simpler and more intuitive way,
//        using very easy words and an analogy if possible.
//        """.formatted(originalExplanation);
//    }

    //generate more accessible descriptions based on existing explanations
    public static String simplifyExplanation(String originalExplanation) {
        return """
                You are helping a learner who already saw an explanation but still didn’t understand it.
        
                Task:
                Rewrite the explanation below in a MUCH simpler way.
        
                Rules:
                - Use ONE short paragraph only.
                - Do NOT use headings, bullet points, or lists.
                - Do NOT add new examples or analogies.
                - Use very simple words and short sentences.
                - Keep it under 60 words.
        
                Rewrite this explanation:
                %s
        """.formatted(originalExplanation);
        // Note: reExplain prompt is intentionally constrained
        // to reduce latency and avoid over-explanation
    }






}
