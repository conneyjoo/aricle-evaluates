package com.xuehai.ae;

import java.util.ArrayList;
import java.util.List;

/**
 * 同义词典的邻接矩阵
 *
 * @author zjy
 * @since 2017-11-05
 */
public class ThesaurusMatrix {

    public static final String SYNONYM_SPLIT_SYMBOL = "|";

    public static final String SYNONYM_GROUP_SPLIT_SYMBOL = ";";

    public static final String DEFAULT_THESAURUS = "ok|okay";

    /** 同义词典文本 */
    private String thesaurus;

    /** 同义词典的分割 */
    private String[] thesaurusSplit;

    /** 同义词典的矩阵 */
    private int[][] matrix;

    /** 邻接矩阵的大小 */
    private int capacity;

    /** 邻接矩阵的索引 */
    private String[] indexes;

    /**
     * 构造方法,初始化工作
     *
     * @param thesaurus 同义词文本
     */
    public ThesaurusMatrix(String thesaurus) {
        this(thesaurus, allSplit(thesaurus).length);
    }

    /**
     * 构造方法,初始化工作
     *
     * @param thesaurus
     * @param capacity 邻接矩阵的大小
     */
    public ThesaurusMatrix(String thesaurus, int capacity) {
        this.thesaurus = thesaurus;
        this.capacity = capacity;
        thesaurusSplit = thesaurus.split(SYNONYM_GROUP_SPLIT_SYMBOL);
        indexes = new String[capacity];
        matrix = new int[capacity][capacity];
        String[] words;
        int index1, index2;

        for (String s : thesaurusSplit) {
            words = s.split("[" + SYNONYM_SPLIT_SYMBOL + "]");

            for (int i = 0, len = words.length; i < len; i++) {
                index1 = hash(words[i]);
                indexes[index1] = words[i];
                matrix[index1][index1] = 1;

                for (int j = (i + 1) % len; j != i; j = (j + 1) % len) {
                    index2 = hash(words[j]);
                    indexes[index2] = words[j];
                    matrix[index1][index2] = 1;
                }
            }
        }
    }

    /**
     * 同义词典的hash算法(后续可以改进)
     *
     * @param word 单词
     * @return int hash值
     */
    private int hash(String word) {
        int hash = (word.hashCode() & Integer.MAX_VALUE) % capacity;

        for (int i = hash, j = 0; j < capacity; i = (i + 1) % capacity, j++) {
            if (indexes[i] == null || indexes[i].equals(word)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 同义词是否存在
     *
     * @param word 单词
     * @return boolean 是否存在
     */
    public boolean hasThesaurus(String word) {
        return hash(word) != -1;
    }

    /**
     * 获取该单词的同义词
     *
     * @param word 单词
     * @return String[] 同义词
     */
    public String[] getThesaurus(String word) {
        List<String> thesaurus = new ArrayList<String>();
        int index = hash(word);

        if (index > -1) {
            int[] row = matrix[index];

            for (int i = 0; i < row.length; i++) {
                if (row[i] == 1) {
                    thesaurus.add(indexes[i]);
                }
            }
        }

        return thesaurus.toArray(new String[]{});
    }

    /**
     * i是否可到j
     *
     * @param i 图的边
     * @param j 图的边
     * @return boolean 是否可达性
     */
    private boolean reachability(int i, int j) {
        return matrix[i][j] == 1;
    }

    /**
     * a,b是否同义词
     *
     * @param a 单词
     * @param b 单词
     * @return boolean 是否相同
     */
    public boolean same(String a, String b) {
        return reachability(hash(a), hash(b));
    }

    /**
     * 销毁占用内存
     */
    public void detroy() {
        thesaurusSplit = null;
        matrix = null;
        indexes = null;
    }

    /**
     * 分割所有单词
     *
     * @param text 文本
     * @return 所有单词
     */
    public static String[] allSplit(String text) {
        return text.split("[" + SYNONYM_SPLIT_SYMBOL + SYNONYM_GROUP_SPLIT_SYMBOL + "]");
    }
}