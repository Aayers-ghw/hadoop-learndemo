# hadoop-learndemo
这是自己学习Hadoop时，写的一些Java客户端代码

一、HDFS  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）hdfs上传文件、下载文件、查看文件信息、创建目录、删除文件或文件夹客户端Java的实现  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）HA机制下hdfs客户端Java的实现  
二、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	Hadoop中的RPC应用实例demo  
三、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	wordcount的实例程序  
四、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	利用hadoop中序列化机制，流量求和程序开发  
五、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	Hadoop的自定义排序实现  
六、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	mr程序中自定义分组的实现  
七、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	倒排索引的mr实现  
八、  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	接下来是一个简单的项目实战--流量分析系统，利用已采集到各网站流量使用量的汇总的数据进行分析   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）从样本数据中提取有代表性的url  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		读入日志数据  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		根据url访问的流量进行排序  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		输出流量占总流量前80%的url  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		利用sqoop将url列表文本数据导入MySQL数据表  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）对原始日志进行分类信息增强  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		读入日志数据过滤脏数据  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		抽取url字段，查询规则分类表  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		添加到原始日志  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		根据分类情况输出两类结果

