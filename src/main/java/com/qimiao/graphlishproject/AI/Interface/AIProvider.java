package com.qimiao.graphlishproject.AI.Interface;

public interface AIProvider {

    //explain word
    String explainWord(String word);

    String reExplain(String explanation);


    /**
     * 第一阶段：生成标准解释（用于存储）
     */
//    WordExplanation generateExplanation(String word);

    /**
     * 第二阶段：基于已有解释，生成更通俗的解释
     */
//    String simplifyExplanation(String explanation);

}
