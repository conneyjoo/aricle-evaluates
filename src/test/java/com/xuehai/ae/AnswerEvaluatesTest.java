package com.xuehai.ae;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AnswerEvaluatesTest {

    @Test
    public void process() {
        String a = "every week/once a week;beautiful village";
        String b = "Yang Tao’s grandparents live in a every week beautiful village The family get together";
        Question question = new Question();
        question.content(a);
        AnswerEvaluates answerEvaluates = new AnswerEvaluates();
        answerEvaluates.question(question);
        answerEvaluates.preprocess();
        double score = answerEvaluates.process(b);
        assertTrue(1 == score);

        a = "every week/once a week;beautiful village";
        b = "Yang Tao’s grandparents live in a beautiful village The family get together every week";
        question = new Question();
        question.content(a);
        answerEvaluates = new AnswerEvaluates();
        answerEvaluates.question(question);
        answerEvaluates.preprocess();
        score = answerEvaluates.process(b);
        assertTrue(1 == score);

        a = "every week/once a week;beautiful village";
        b = "Yang Tao’s grandparents live in a beautiful The family get together once a week";
        question = new Question();
        question.content(a);
        answerEvaluates = new AnswerEvaluates();
        answerEvaluates.question(question);
        answerEvaluates.preprocess();
        score = answerEvaluates.process(b);
        assertTrue(0.5 == score);

        a = "every week/once a week;beautiful village";
        b = "Yang Tao’s grandparents live in a perty village The family get together every week";
        question = new Question();
        question.content(a);
        answerEvaluates = new AnswerEvaluates();
        answerEvaluates.question(question);
        answerEvaluates.preprocess();
        score = answerEvaluates.process(b);
        assertTrue(0.5 == score);

        a = "Every week/Once a week";
        b = "Is it famous isn't about once a week and.";
        question = new Question();
        question.content(a);
        answerEvaluates = new AnswerEvaluates();
        answerEvaluates.setFilterPunctuation(true);
        answerEvaluates.question(question);
        answerEvaluates.preprocess();
        score = answerEvaluates.process(b);
        assertTrue(1 == score);
    }
}
