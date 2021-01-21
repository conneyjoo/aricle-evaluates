package com.xuehai.ae;

public class Question {

    private QuestionType type;

    private String content;

    private String thesaurus = "ok|okay";

    public String content() {
        return content;
    }

    public void content(String content) {
        this.content = content.toLowerCase();
    }

    public QuestionType type() {
        return type;
    }

    public void type(QuestionType type) {
        this.type = type;
    }

    public String thesaurus() {
        return thesaurus;
    }

    public void thesaurus(String thesaurus) {
        this.thesaurus = thesaurus.toLowerCase();
    }
}
