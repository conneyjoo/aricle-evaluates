package com.xuehai.ae;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SketchEvaluatesTest {

    @Test
    public void process() {
        String a, b;
        Question question;
        SketchEvaluates evaluates;

        a = "Every great student hopes to have nice teachers";
        b = "Every good student hopes to have good teachers";
        question = new Question();
        question.content(a);
        question.thesaurus("great|best|good|nice;love|like");
        evaluates = new SketchEvaluates();
        evaluates.question(question);
        evaluates.preprocess();
        evaluates.process(b);
        assertTrue(8 == evaluates.getWordCount());

        a = "Every great student great hopes to best have nice teachers plenty of plenty of";
        b = "Every good student hopes to have good teachers plenty of a number of";
        question = new Question();
        question.content(a);
        question.thesaurus("great|best|good|nice;love|like;hello|hi;plenty of|a number of");
        evaluates = new SketchEvaluates();
        evaluates.question(question);
        evaluates.preprocess();
        evaluates.process(b);
        assertTrue(10 == evaluates.getWordCount());

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
        question.thesaurus("i am|im;ok|okay");
        evaluates = new SketchEvaluates();
        evaluates.setFilterPunctuation(true);
        evaluates.question(question);
        evaluates.preprocess();
        evaluates.process(b);
        assertTrue(97 == evaluates.getWordCount());

        a = "Every student hopes to have nice teachers and they can study in a good class They can be allowed to take part in many after class activities They can also be allowed to make fewer but better class rules They will be able to communicate with teachers in a friendly way If some students have problems in life or study they should be encouraged by teachers";
        b = "Every student hopes to have good teachers and they can study in a good class they can be allowed to take part in plenty of, class activities they can also be allowed to make fewer but better class rules, they can be able to communicate with teachers in a friendly way will some students need, in life of study that should be encouraged by teachers.";
        question = new Question();
        question.content(a);
        question.thesaurus("nice|good|excellent;plenty of|many|a number of;can|will;meet|have;difficulty|problems");
        evaluates = new SketchEvaluates();
        evaluates.setFilterPunctuation(true);
        evaluates.question(question);
        evaluates.preprocess();
        evaluates.process(b);
    }
}
