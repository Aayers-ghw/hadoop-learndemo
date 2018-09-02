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
 * ��������һ���ض�����ҵ
 * ���磬����ҵʹ���ĸ�����Ϊ�߼������е�map���ĸ���Ϊreduce
 * ������ָ������ҵҪ������������ڵ�·��
 * ������ָ������ҵ����Ľ���ŵ��ĸ�·��
 * 
 * @author Aayers-ghw
 *
 */
public class WCRunner {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();
		
		Job wcjob = Job.getInstance(conf, "hadoop");
		
		//��������jar���õ���Щ�����ĸ�jar��
		wcjob.setJarByClass(WCRunner.class);
		
		
		//��jobʹ�õ�mapper��reducer����
		wcjob.setMapperClass(WCMapper.class);
		wcjob.setReducerClass(WCReducer.class);
		
		//ָ��reducer���������kv����
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		
		//ָ��mapper���������kv����
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		
		//ָ��Ҫ������������ݴ��·��
		FileInputFormat.setInputPaths(wcjob, new Path("/wc/srcdata/"));
		
		//ָ��Ҫ��������������·��
		FileOutputFormat.setOutputPath(wcjob, new Path("/wc/output2/"));		
		
		//��job�ύ����Ⱥ����
		wcjob.waitForCompletion(true);
		
		
	}

}
