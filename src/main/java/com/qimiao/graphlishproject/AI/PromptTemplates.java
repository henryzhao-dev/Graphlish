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
    public static String simplifyExplanation(String explanation) {
        return """
        You are teaching a beginner.

        The following is a dictionary-style explanation:

        %s

        Please explain it again in a much simpler and more intuitive way,
        using very easy words and an analogy if possible.
        """.formatted(explanation);
    }




}
