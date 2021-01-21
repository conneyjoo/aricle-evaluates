package com.xuehai.ae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class AnswerEvaluates extends Evaluates {

    private static final Logger logger = LoggerFactory.getLogger(AnswerEvaluates.class);

    private String[] contentSplit;

    private LinkedList<Integer> sorts = new LinkedList<Integer>();

    @Override
    public void preprocess() {
        contentSplit = question().content().split(";");
    }

    @Override
    public double process(String text) {
        text = (isFilterPunctuation() ? filterPunctuation(text) : text).toLowerCase() + " ";
        String[] words;
        int index = 0;

        for (String content : contentSplit) {
            words = content.split("/");

            for (String word : words) {
                word = (isFilterPunctuation() ? filterPunctuation(word) : word).trim();

                if (text.contains(word + " ")) {
                    sorts.add(index++);
                    break;
                }
            }
        }

        double score = ((double) sorts.size()) / contentSplit.length;

        if (logger.isInfoEnabled()) {
            logger.info("关键词:\n{}\n阅读:\n{}\n得分:\n{}", question().content(), text, score);
        }

        return score;
    }

    public String getWords() {
        StringBuffer buffer = new StringBuffer();

        if (sorts.size() > 0) {
            buffer.append(contentSplit[sorts.get(0)]);
            for (int i = 1, len = sorts.size(); i < len; i++) buffer.append(" ").append(contentSplit[sorts.get(i)]);
        }

        return buffer.toString();
    }

    @Override
    public int getWordCount() {
        return sorts.size();
    }

    @Override
    public LinkedList<Integer> getWordIndexes() {
        return sorts;
    }

    public double getScore() { return ((double) sorts.size()) / contentSplit.length; }

    @Override
    public void destroy() {
        if (sorts != null) sorts.clear();

        contentSplit = null;
        sorts = null;
    }
}