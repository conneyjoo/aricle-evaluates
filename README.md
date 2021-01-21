作文评测设计初稿1.0

1.业务分析：
根据当前讨论结果，以《宁波中考口语人机对话样题》样例对题目分为三类，朗读短文，情景回答，话题简述，每一类题目都有不同的评判标准，系统需要根据这些标准制定规则进行最终的评分．

1.朗读短文:要求学生对以下题目中的短文从左至右顺序一字不错的朗读出来．
第三部分 朗读短文（计3分）：
I love reading. I spend over seven hours a week reading different types of books. On weekdays, I usually read for about half an hour before going to bed. I read a lot at the weekend. I am interested in history books, but I like novels best. The four great classical Chinese novels are my favorite. I get most of my books from Sunshine Library—is just opposite my home. My friends give me lots of advice on books. We often meet together and discuss what to read. Reading is always a wonderful time. Good books are good friends. They also open up a whole new world to me.

２.情景回答:要求学生对以下题目种的情景给出一段英语描述，而描述当中只需有红色的关键词出现即可．
第四部分 情景问答（计2分）：
请看下面的情景提示：
杨涛的祖父母住在一个美丽的村庄。杨涛和爸爸妈妈每周去看望他们。

问题录音文字：
1. Where do Yang Tao’s grandparents live?
2. How often do all the family get together?

1. In a beautiful village. Yang Tao’s grandparents/They live in a beautiful village. (>=4 words)
2. Every week/ Once a week. The family get together every week/ once a week.

３.话题简述 :要求学生根据要点提示用英语进行话题表述，表述中约束句数和时间，表述内容要求出现红色关键词，其中个别关键词可以用同义词进行代替，比如：nice换成good；plenty of换成a number of；difficulty换成problems；更多的鼓励可以说成more encouraged
第五部分 话题简述（计5分）：
要点：
1.每个学生都希望拥有优秀的老师，在良好的班级学习。
2.他们可以被允许参加丰富的课外活动；他们可以被允许制定更少但是更优良的班规。
3.他们可以经常和老师进行友好的交流；如果同学在生活或学习中遇到困难，应该得到老师更多的鼓励。
Every student hopes to have nice teachers, and they can study in a good class. They can be allowed to take part in plenty of after-class activities. They can also be allowed to make fewer but better class rules. They can communicate with teachers in a friendly way. If some students meet difficulty in life or study, they should be encouraged by teachers.









2.评测处理流程








































根据题目编号获取题目模型，根据题目类型创建评测器，对作文进行预处理，评测，返回得分(评测百分比结果)．


3.题目模型
题目编号：题目编号
题目类型：朗读短文，情景回答，话题简述
题目分数：所得分
题目显示文本：显示给用户看的文本
文本关键词：评测对比数据
文本同义词： nice,good;plenty of,a number of;…
文本句数：要求答案的句数
文本时间：要求答案的时间



4.评测模型
评测器的抽象：
预处理：评测前的一些准备处理．
评测：评测短文内容，输出结果．

评测器的实现：
朗读短文评测器
预处理：
1.去除标点符号．
2.按空格拆分单词．
3.按从左至右顺序为每个单词标记顺序号．
4.用hash表存储所有单词，hash表key为单词，value使用queue来存储单词顺序号．

短文内容
I love reading. I spend over seven hours a week reading different types of books. On weekdays, I usually read for about half an hour before going to bed
处理后的结果：
hash table
I - queue [0, 3...]
love - queue [1]
reading - queue [2]
...

评测：
首先把学生的阅读内容按空格划分单词，每个单词根据出现的次数标记一个计数器(wordCount)，然后按从左至右顺序到预处理的hash表里面找到所有位置保存到一个集合(sorts)，hash表每命中一次wordCount--，然后从sorts中找出最长有序子集，最后再从sorts中找出最接近阅读顺序的子集．
sorts的添加规则：
1.wordCount等于hash表中queue.size则单词位置添加到sorts并从queue中移除．
2.queue.size大于wordCount则判断sorts.size等于０的话单词位置添加到sorts并从queue中移除，不等于０则取queue中大于sorts最后一个元素的单词位置添加到sorts并从queue中移除．
3.wordCount大于hash表中queue.size则单词位置添加到sorts．

sorts中找出最长有序子集：
比如
0 -> 2 -> 0 -> 1 -> 3
最长有序集合
0 -> 1 -> 3

sorts找出最接近阅读顺序子集：
短文:
I love reading I spend over seven hours a week reading different types of books
阅读:
spend love reading different
处理结果:
4 -> 1 -> 2 -> 11
最长有序子集:
1 -> 2 -> 11
love(1)  reading(2)  different(11)
最接近阅读顺序子集:
1 -> 10 -> 11
love(1)  reading(10)  different(11)
reading在短文中出现２次，最接近阅读的应该是第10个位置的reading，只需要在hash表的queue中找出2和11之间的顺序替换后重复步骤直到循环跳出为止．

分数的计算：
sorts.size / 短文单词数量 * 题目分数


性能测试
短文
I love reading I spend over seven hours a week reading different types of books On weekdays I usually read for about half an hour before going to bed I read a lot at the weekend I am interested in history books but I like novels best The four great classical Chinese novels are my favorite I get most of my books from Sunshine Library—is just opposite my home My friends give me lots of advice on books We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends They also open up a whole new world to me

阅读
I spend love I@ reading I spend over spend seven hours a week reading different types of books On weekdays I usually read for about half an@ hour before going to bed I read@ a lot at the weekend I am interested in history books but I like novels best The four great classical Chinese novels are my favorite I get most of my books from@ Sunshine Library—is just opposite my home My friends give me lots of advice on books We often meet together and discuss what to read Reading is always a wonderful time Good books are good friends They also open up a whole new world to me@
1 个线程，5 秒内总共执行的事物数量：231652
执行的TPS: 46330
每秒执行46330次

测试样例查看附件test


情景回答评测器
预处理：
无

评测：
回答内容
1. In a beautiful village. Yang Tao’s grandparents/They live in a beautiful village. (>=4 words)
2. Every week/ Once a week. The family get together every week/ once a week.
判断学生阅读的内容是否包含红色关键字
contains(beautiful village) && (contains(Every week) || contains(Once a week))

话题描述评测器
预处理：
1.和<朗读短文预处理>一样，不同的是加入同义词标记，修改顺序号采用24位数据存储，前16存储单词所在位置，后８位存储是否同义词大于等于1是0否．
2.同义词采用图的邻接矩阵结构存储
设G=V,E是一个同义词的图，V顶点，E边，顶点为单词，边为同义词关系，则他们的邻接矩阵是Mij的一个二维数组．
同义词:
Good 
Nice 
Great 
Best
plenty of
a number of
difficulty
problems
构成的邻接矩阵，1/0表示:存在/不存在关系
	Good	Nice	Great	Best	plenty of	a number of	difficulty	problems
Good	1	1	1	1	0	0	0	0
Nice	1	1	1	1	0	0	0	0
Great	1	1	1	1	0	0	0	0
Best	1	1	1	1	0	0	0	0
plenty of	0	0	0	0	1	1	0	0
a number of	0	0	0	0	1	1	0	0
difficulty	0	0	0	0	0	0	1	1
problems	0	0	0	0	0	0	1	1
Good到Nice是１，到plenty of是0
plenty of到a number of是1，到Good是0

hash邻接矩阵的构成：
关键词所在邻接矩阵行列位置由关键词hash算法决定的，如果发生碰撞或行列已经存在关键词则以环形的方式向后移．
hash算法: word.hashcode & 0x7FFFFFFF % 同义词总数

评测：
1.和<朗读短文评测>一样，不同的是取顺序号后８位判断是否大于等于１，如果是则判断该词在邻接矩阵是否可达性．
2.时间检测由外部处理
3.句数检测待讨论


