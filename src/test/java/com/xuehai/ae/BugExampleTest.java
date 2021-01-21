package com.xuehai.ae;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BugExampleTest {

    @Test
    public void process() {
        String a, b;
        ReadEvaluates evaluates;
        Question question;

        a = "I love reading I spent over seven hours a week with different types of books on weekdays I usually read for about half an hour before going to bed I read a lot at the weekend, I am interested in history books but I like noodles best great classical chinese novels are my favorite I get most of my books from sunshine laborers, just opposite my home my friends give me lots of advice on books we often meet together and discuss what to read, reading is always a wonderful time good books are good friends, they also number who knew was to be.";
        b = "I love reading. I spend over seven hours a week reading different types of books. On weekdays, I usually read for about half an hour before going to bed. I read a lot at the weekend. I am interested in history books, but I like novels best. The four great classical Chinese novels are my favorite. I get most of my books from Sunshine Libraryis just opposite my home. My friends give me lots of advice on books. We often meet together and discuss what to read. Reading is always a wonderful time. Good books are good friends. They also open up a whole new world to me.";
        XHEvaluatesBuilder.Result result = XHEvaluatesBuilder.evaluate(QuestionType.READ, a, b);
        System.out.println(result.getScore());
        System.out.println(result.getWordIndexes());
    }
}
