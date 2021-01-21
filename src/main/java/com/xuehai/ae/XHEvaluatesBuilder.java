package com.xuehai.ae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class XHEvaluatesBuilder {

    private static final Logger logger = LoggerFactory.getLogger(XHEvaluatesBuilder.class);

    public static class Result {

        private int wordCount;
        private String words;
        private Integer[] wordIndexes;
        private double score;

        Result(int wordCount, String words, Integer[] wordIndexes, double score) {
            this.wordCount = wordCount;
            this.words = words;
            this.wordIndexes = wordIndexes;
            this.score = score;
        }

        public int getWordCount() {
            return wordCount;
        }

        public void setWordCount(int wordCount) {
            this.wordCount = wordCount;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public Integer[] getWordIndexes() {
            return wordIndexes;
        }

        public void setWordIndexes(Integer[] wordIndexes) {
            this.wordIndexes = wordIndexes;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    };

    /**
     * 短文评测,根据不同的题目类型对答案和学生阅读内容的相似度给出评分
     * @param type　READ(朗读短文), ANSWER(情景问答), SKETCH(主题简述)
     * @param args 0:短文内容, 1:学生阅读内容, 2:同义词典
     * @return
     */
    public static Result evaluate(QuestionType type, String ...args) {
        if (args.length < 2) {
            logger.error("args must is [aricle, read content]");
            return null;
        }

        Evaluates evaluates = null;
        Question question = new Question();
        question.type(type);
        question.content(args[0]);
        String text = args[1];
        double score = -1;

        if (type == QuestionType.READ) {
            evaluates = new SketchEvaluates();
        } else if (type == QuestionType.ANSWER) {
            evaluates = new AnswerEvaluates();
        } else if (type == QuestionType.SKETCH) {
            if (args.length > 2) {
                question.thesaurus(args[2]);
            }

            evaluates = new SketchEvaluates();
        }

        Result result = null;

        if (evaluates != null) {
            evaluates.setFilterPunctuation(true);
            evaluates.question(question);
            evaluates.preprocess();
            evaluates.process(text);
            result = new Result(evaluates.getWordCount(), evaluates.getWords(), evaluates.getWordIndexes().toArray(new Integer[]{}), evaluates.getScore());
            evaluates.destroy();
        }

        return result;
    }

    public static void main(String[] args) {
        String a = "i am a teacher";
        String b = "am i a teacher";
        Result result = XHEvaluatesBuilder.evaluate(QuestionType.READ, a, b);
        System.out.println(result.getScore());

        a = "ok I love reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        b = "okay I spend love I@ reading I spend over spend seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an@ hour before going to bed I read@ a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from@ Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me@";
        result = XHEvaluatesBuilder.evaluate(QuestionType.READ, a, b);
        System.out.println(result.getScore());
    }
}
