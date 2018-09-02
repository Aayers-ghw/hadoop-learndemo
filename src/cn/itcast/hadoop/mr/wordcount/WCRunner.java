package cn.itcast.hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 用来描述一个特定的作业
 * 比如，该作业使用哪个类作为逻辑处理中的map，哪个作为reduce
 * 还可以指定该作业要处理的数据所在的路径
 * 还可以指定该作业输出的结果放到哪个路径
 * 
 * @author Aayers-ghw
 *
 */
public class WCRunner {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();
		
		Job wcjob = Job.getInstance(conf, "hadoop");
		
		//设置整个jar所用的那些类在哪个jar包
		wcjob.setJarByClass(WCRunner.class);
		
		
		//本job使用的mapper和reducer的类
		wcjob.setMapperClass(WCMapper.class);
		wcjob.setReducerClass(WCReducer.class);
		
		//指定reducer的输出数据kv类型
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		
		//指定mapper的输出数据kv类型
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		
		//指定要处理的输入数据存放路径
		FileInputFormat.setInputPaths(wcjob, new Path("/wc/srcdata/"));
		
		//指定要处理结果的输出存放路径
		FileOutputFormat.setOutputPath(wcjob, new Path("/wc/output2/"));		
		
		//讲job提交给集群运行
		wcjob.waitForCompletion(true);
		
		
	}

}
