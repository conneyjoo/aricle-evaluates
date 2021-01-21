package com.xuehai.ae;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReadEvaluatesTest {

    @Test
    public void process() {
        String a, b;
        ReadEvaluates readEvaluates;
        Question question;

        a = "i am a teacher";
        b = "am i a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "am i a teacher teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "i am i a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "i a i am teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "teacher am i a";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(2 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "teacher a xxx i xx am teacher am i a xxx";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "teacher a xxx i xx am teacher am i a xxx teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i a am a teacher";
        b = "i am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i a am a teacher";
        b = "i a am teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "i a am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i a am a teacher";
        b = "i a am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(5 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "a i am teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "i hello ok i a am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "hello all i am a teacher";
        b = "i hello ok i a am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(5 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "is hello ok i a amc a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "i am a lovely perty teacher";
        b = "i perty am a lovely perty teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(6 == readEvaluates.getWordCount());

        a = "i am a lovely perty teacher";
        b = "lovely xxx as xxx xxx i xx pertyxx a am a am lovely pertyx teacherxx";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "i am a teacher";
        b = "i";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(1 == readEvaluates.getWordCount());

        a = "i am am a a teacher";
        b = "i am am a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(5 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "I love reading I spend over seven hours a week reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(15 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "I love reading I spend over";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(6 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "seven hours a week";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(5 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "love reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(6 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "over love reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(6 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "I a love reading I love spend reading over seven hours a week reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(15 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "I a love reading I love spendd reading over seven hours a week reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "we love reading I love spendd reading over seven hours a week reading different types of books";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(13 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "love spend reading different";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(4 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books";
        b = "spend love reading different";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        b = "I love reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(108 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        b = "love I reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(107 == readEvaluates.getWordCount());

        a = "I love reading I spend over seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an hour before going to bed I read a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me";
        b = "I spend love I@ reading I spend over spend seven hours a week reading different types of books On weekdays " +
                "I usually read for about half an@ hour before going to bed I read@ a lot at the weekend " +
                "I am interested in history books but I like novels best The four great classical Chinese novels are my favorite " +
                "I getThesaurus most of my books from@ Sunshine Library—is just opposite my home My friends give me lots of advice on books " +
                "We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends " +
                "They also open up a whole new world to me@";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(104 == readEvaluates.getWordCount());

        a = "teacher a mathmatic My mathmatic a teacher";
        b = "mathmatic a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "teacher a mathmatic My mathmatic mathmatic mathmatic a teacher";
        b = "mathmatic a teacher";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(3 == readEvaluates.getWordCount());

        a = "I love reading. I spend over seven hours a week reading different types of books. On weekdays, " + //16
                "I usually read for about half an hour before going to bed. I read a lot at the weekend. " + //35
                "I am interested in history books, but I like novels best. The four great classical Chinese novels are my favorite. " + //55
                "I get most of my books from Sunshine Libraryis just opposite my home. My friends give me lots of advice on books. " + //77
                "We often meet together and discuss what to read. Reading is always a wonderful time. Good books are good friends. " +
                "They also open up a whole new world to me.";
        b = "I love reading I spent over seven hours a week trading different types of books on weekdays " +
                "are usually read for about half an hour, before going to bed I read a lot at the weekend " +
                "i'm interested in history books but I like normal system the four great clash a chinese novels are my favorite " +
                "I get most of my books from sunshine labour is just opposite my home, my friends give me lots of advice on books " +
                "we often meet together and delicious was told read reading is always is a wonderful time, good books books are good friends " +
                "they also open up a whole new world to me, ah.";
        question = new Question();
        question.content(a);
        readEvaluates = new ReadEvaluates();
        readEvaluates.setFilterPunctuation(true);
        readEvaluates.question(question);
        readEvaluates.preprocess();
        readEvaluates.process(b);
        assertTrue(96 == readEvaluates.getWordCount());
    }
}
