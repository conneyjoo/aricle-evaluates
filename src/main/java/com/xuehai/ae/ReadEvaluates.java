package com.xuehai.ae;

import com.xuehai.ae.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 短文阅读评测
 * 对a,b文本做相似度匹配,支持多读,错读,但最终b中的单词要求按a中的单词顺序一一出现
 *
 * @author zjy
 * @since 2017-11-05
 */
public class ReadEvaluates extends Evaluates {

    private static final Logger logger = LoggerFactory.getLogger(ReadEvaluates.class);

    /** 短文内容 */
    protected String content;

    /** 按空格分割的短文单词 */
    protected String[] contentSplit;

    /** 单词顺序表 */
    protected Map<String, LinkedList<Integer>> wordSorts;

    /** 阅读正确的顺序集合 */
    protected LinkedList<Integer> sorts;

    public ReadEvaluates() {}

    /**
     * 短文阅读评测预处理
     */
    public void preprocess() {
        preprocess(question().content());
    }

    /**
     * 短文阅读评测预处理
     *
     * @param text 短文文本
     */
    public void preprocess(String text) {
        content = isFilterPunctuation() ? filterPunctuation(text) : text;
        contentSplit = content.split(" ");
        wordSorts = new HashMap<String, LinkedList<Integer>>();
        sorts = new LinkedList<Integer>();
        String word;
        LinkedList<Integer> queue;

        for (int i = 0, len = contentSplit.length; i < len; i++) {
            word = contentSplit[i];
            queue = get(wordSorts, word);

            if (queue == null) {
                queue = new LinkedList<Integer>();
                set(wordSorts, word, queue);
            }

            queue.add(i);
        }
    }

    /**
     * 短文阅读评测处理
     *
     * @param text 阅读文本
     * @return double 处理后的相似度百分比
     */
    public double process(String text) {
        text = text.toLowerCase();
        String[] textSplit = (isFilterPunctuation() ? filterPunctuation(text) : text).split(" ");
        String word;
        LinkedList<Integer> queue;
        Map<String, AtomicInteger> wordCounts = totalWordCount(textSplit);
        AtomicInteger wordCount;

        for (int i = 0, len = textSplit.length; i < len; i++) {
            word = textSplit[i];
            queue = get(wordSorts, word);
            wordCount = get(wordCounts, word);

            if (queue != null) {
                if (wordCount.intValue() == queue.size()) {
                    sorts.add(queue.poll());
                } else if (queue.size() > wordCount.intValue()) {
                    sorts.add(sorts.size() == 0 ? queue.poll() : getThanSort(queue, sorts.getLast()));
                } else if (wordCount.intValue() > queue.size()) {
                    if (queue.peek() < getNextSort(textSplit, i)) {
                        sorts.add(queue.poll());
                    } else {
                        sorts.add(queue.peek());
                    }
                }

                wordCount.decrementAndGet();
                if (queue.size() == 0) remove(wordSorts, word);
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("短文:\n{}\n阅读:\n{}\n处理结果:\n{}", question().content(), text, StringUtils.join(sorts, " -> "));
        }

        sorts = maxLengthSorts(sorts);
        closestSorts(sorts);
        return getScore(sorts);
    }

    protected <V> V get(Map<String, V> map, String word) {
        return map.get(word);
    }

    protected <V> V set(Map<String, V> map, String word, V value) {
        return map.put(word, value);
    }

    protected <V> V remove(Map<String, V> map, String word) {
        return map.remove(word);
    }

    /**
     * 统计相同单词出现的次数
     *
     * @param text 阅读文本
     * @return Map<String, AtomicInteger> 单词出现的次数
     */
    private Map<String, AtomicInteger> totalWordCount(String[] textSplit) {
        Map<String, AtomicInteger> wordCounts = new HashMap<String, AtomicInteger>();
        AtomicInteger count;
        String word;

        for (int i = 0, len = textSplit.length; i < len; i++) {
            word = textSplit[i];
            count = get(wordCounts, word);

            if (count == null) {
                count = new AtomicInteger(0);
                set(wordCounts, word, count);
            }

            count.incrementAndGet();
        }

        return wordCounts;
    }

    /**
     * 在当前的阅读顺序上向后获取短文单词所在顺序,比如
     * hello queue [5, 10]
     * sorts [1, 2, 3, 6]
     * sorts向后获取最后一个6比５大所以,该方法只会获取hello(10)的位置
     *
     * @param queue 短文单词的顺序
     * @param than 比较的数字
     * @return int 阅读顺序上向后获取短文单词所在顺序
     */
    private int getThanSort(LinkedList<Integer> queue, Integer than) {
        for (ListIterator<Integer> iterator = queue.listIterator(); iterator.hasNext();) {
            int order = iterator.next();
            if (than < order) {
                iterator.remove();
                return order;
            }
        }

        return queue.poll();
    }

    /**
     * 获取阅读后续单词的位置
     *
     * @param textSplit 空格分割的单词
     * @param start 遍历的起始位置
     * @return int 阅读后续单词的位置
     */
    private int getNextSort(String[] textSplit, int start) {
        LinkedList<Integer> next = null;

        for (int i = start + 1, len = textSplit.length; i < len; i++) {
            next = get(wordSorts, textSplit[i]);

            if (next != null) {
                return next.peek();
            }
        }

        return -1;
    }

    /**
     * 找出最长的有序子集
     *
     * @param sorts 阅读正确的顺序集合
     * @return List<Integer> 最长的有序子集
     */
    private LinkedList<Integer> maxLengthSorts(LinkedList<Integer> sorts) {
        return maxLengthSorts(sorts, null, 0);
    }

    /**
     * 找出最长的有序子集
     *
     * @param sorts 阅读正确的顺序集合
     * @param maxLengthSorts 最长的有序子集
     * @param start 遍历的起始位置
     * @return List<Integer> 最长的有序子集
     */
    private LinkedList<Integer> maxLengthSorts(LinkedList<Integer> sorts, LinkedList<Integer> maxLengthSorts, int start) {
        if (maxLengthSorts == null) {
            maxLengthSorts = new LinkedList<Integer>();
        }

        LinkedList<Integer> list = new LinkedList<Integer>();
        int prevnum = -1, num = 0, skip = 0;

        if (maxLengthSorts.size() > 0) {
            int min = sorts.get(start), sort;

            for (int i = maxLengthSorts.size() - 1; i > -1; i--) {
                sort = maxLengthSorts.get(i);

                if (sort < min) {
                    list.addFirst(sort);
                    min = sort;
                } if (start < sorts.size() - 1 && (sort > min && sort < sorts.get(start + 1))) {
                    list.addFirst(sort);
                    min = sort;
                    skip++;
                }
            }
        }

        for (int i = start + skip, len = sorts.size(); i < len; i++) {
            num = sorts.get(i);

            if (num > prevnum) {
                list.add(num);
                prevnum = num;
            } else {
                return maxLengthSorts(sorts, list.size() > maxLengthSorts.size() ? list : maxLengthSorts, i);
            }
        }

        return list.size() > maxLengthSorts.size() ? list : maxLengthSorts;
    }

    /**
     * 找出最最近阅读顺序的子集
     *
     * @param sorts 阅读正确的顺序集合
     */
    private void closestSorts(LinkedList<Integer> sorts) {
        int prevnum = 0, num = 0;
        LinkedList<Integer> queue;

        for (int i = 1; i < sorts.size(); i++) {
            prevnum = sorts.get(i - 1);
            num = sorts.get(i);
            queue = wordSorts.get(contentSplit[prevnum]);

            if (queue != null) {
                for (Integer closest : queue) {
                    if (prevnum < closest && closest < num) {
                        sorts.set(i - 1, closest);
                    }
                }
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("找出最接近阅读顺序子集:\n{}", StringUtils.join(sorts, " -> "));
        }
    }

    /**
     * 获取短文和阅读相似度的百分比
     *
     * @param sorts 阅读正确的顺序集合
     * @return double 相似度百分比
     */
    private double getScore(LinkedList<Integer> sorts) {
        double score = new BigDecimal((double) sorts.size() / contentSplit.length).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (logger.isInfoEnabled()) {
            logger.info("单词总数:{}, 阅读正确总数:{}, 分数:{}\n", contentSplit.length, sorts.size(), score);
        }

        return score;
    }

    /**
     * 获取短文和阅读相似度的百分比
     *
     * @return double 相似度百分比
     */
    public double getScore() {
        return getScore(sorts);
    }

    /**
     * 获取阅读正确的单词
     *
     * @return String 阅读正确的单词
     */
    @Override
    public String getWords() {
        StringBuffer buffer = new StringBuffer();

        if (sorts.size() > 0) {
            buffer.append(contentSplit[sorts.get(0)]);
            for (int i = 1, len = sorts.size(); i < len; i++) buffer.append(" ").append(contentSplit[sorts.get(i)]);
        }

        return buffer.toString();
    }

    /**
     * 获取阅读正确的单词数量
     *
     * @return int 阅读正确的单词数量
     */
    @Override
    public int getWordCount() {
        return sorts.size();
    }

    /**
     * 获取阅读正确的单词位置
     *
     * @return List<Integer> 阅读正确的单词位置集合
     */
    @Override
    public LinkedList<Integer> getWordIndexes() {
        return sorts;
    }

    /**
     * 销毁占用内存
     */
    @Override
    public void destroy() {
        if (wordSorts != null) wordSorts.clear();
        if (sorts != null) sorts.clear();

        content = null;
        contentSplit = null;
        wordSorts = null;
        sorts = null;
    }
}
