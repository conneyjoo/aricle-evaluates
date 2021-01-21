package com.xuehai.ae;

import java.util.LinkedList;

/**
 * 短文评测抽象类
 *
 * @author zjy
 * @since 2017-11-05
 */
public abstract class Evaluates {

    private Question question;

    private boolean isFilterPunctuation = false;

    public abstract void preprocess();

    public abstract double process(String text);

    public abstract void destroy();

    public abstract String getWords();

    public abstract int getWordCount();

    public abstract LinkedList<Integer> getWordIndexes();

    public abstract double getScore();

    public Evaluates() {}

    /**
     * 过滤标点符号
     *
     * @param text 短文文本
     * @return 过滤后的文本
     */
    public String filterPunctuation(String text) {
        text = text.replaceAll("[-—]", " ").replaceAll("[^a-zA-Z0-9\\s]", "");
        return text;
    }

    public void question(Question question) {
        this.question = question;
    }

    public Question question() {
        return question;
    }

    public boolean isFilterPunctuation() {
        return isFilterPunctuation;
    }

    public boolean setFilterPunctuation(boolean isFilterPunctuation) {
        this.isFilterPunctuation = isFilterPunctuation;
        return isFilterPunctuation;
    }
}
