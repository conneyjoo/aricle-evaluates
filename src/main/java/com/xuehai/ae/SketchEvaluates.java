package com.xuehai.ae;

import com.xuehai.ae.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xuehai.ae.ThesaurusMatrix.*;

public class SketchEvaluates extends ReadEvaluates {

    private static final Logger logger = LoggerFactory.getLogger(SketchEvaluates.class);

    private ThesaurusMatrix tm;

    private String[] multiWordSynonyms;

    public void preprocess() {
        if (question().thesaurus() == null) {
            question().thesaurus(DEFAULT_THESAURUS);
        }

        String[] allWords = allSplit(question().thesaurus());
        multiWordSynonyms = findMultiWordSynonym(allWords);
        tm = new ThesaurusMatrix(question().thesaurus().replaceAll(" ", ""), allWords.length);

        super.preprocess(eliminateMultiWordSynonym(question().content()));
    }

    @Override
    public double process(String text) {
        return super.process(eliminateMultiWordSynonym(text.toLowerCase()));
    }

    @Override
    protected <V> V get(Map<String, V> map, String word) {
        String[] thesaurus = tm.getThesaurus(word);
        return super.get(map, thesaurus.length > 0 ? StringUtils.join(thesaurus, SYNONYM_SPLIT_SYMBOL) : word);
    }

    @Override
    protected <V> V set(Map<String, V> map, String word, V value)  {
        String[] thesaurus = tm.getThesaurus(word);
        return super.set(map, thesaurus.length > 0 ? StringUtils.join(thesaurus, SYNONYM_SPLIT_SYMBOL) : word, value);
    }

    @Override
    protected <V> V remove(Map<String, V> map, String word) {
        String[] thesaurus = tm.getThesaurus(word);
        return super.remove(map, thesaurus.length > 0 ? StringUtils.join(thesaurus, SYNONYM_SPLIT_SYMBOL) : word);
    }

    private String[] findMultiWordSynonym(String[] thesaurusSplit) {
        List<String> list = new ArrayList<String>();
        String synonym;

        for (int i = 0, len = thesaurusSplit.length; i < len; i++) {
            synonym = thesaurusSplit[i];

            if (synonym.indexOf(" ") > 0) {
                list.add(synonym);
            }
        }

        return list.toArray(new String[]{});
    }

    private String eliminateMultiWordSynonym(String text) {
        StringBuffer buf = new StringBuffer(text);
        int start, end;

        for (String multiWordSynonym : multiWordSynonyms) {
            start = buf.indexOf(multiWordSynonym);
            end = start + multiWordSynonym.length();

            while (start > -1 && start < end) {
                buf.replace(start, end, multiWordSynonym.replaceAll(" ", ""));
                start = buf.indexOf(multiWordSynonym);
                end = start + multiWordSynonym.length();
            }
        }

        return buf.toString();
    }

    @Override
    public void destroy() {
        super.destroy();
        tm.detroy();
        multiWordSynonyms = null;
    }
}
