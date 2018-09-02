package cn.itcast.hadoop.mr.ii;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.itcast.hadoop.mr.flowsort.SortMR;
import cn.itcast.hadoop.mr.flowsort.SortMR.SortMapper;
import cn.itcast.hadoop.mr.flowsort.SortMR.SortReducer;
import cn.itcast.hadoop.mr.flowsum.FlowBean;

/**
 * ����������һjob
 * @author Aayers-ghw
 *
 */
public class InverseIndexStepOne {

	public static class StepOneMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			
			//�õ�һ������
			String line = value.toString();
			//�зֳ���������
			String[] fields = StringUtils.split(line, "\t");
			//��ȡ��һ���������ڵ��ļ���Ƭ
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			//���ļ���Ƭ�л�ȡ�ļ���
			String fileName = inputSplit.getPath().getName();
			
			for (String field : fields) {
				
				//��װkv�����k : hello-->a.text	v: 1 
				context.write(new Text(field + "-->" + fileName), new LongWritable(1));
			}
		} 
	}
	
	public static class StepOneReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		
		//<hello-->a.txt,{1,1,1,...}>
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context context)
				throws IOException, InterruptedException {
			
			long counter = 0;
			for(LongWritable value : values){
				
				counter += value.get();
			}
			
			context.write(key, new LongWritable(counter));
		}
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();	
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(InverseIndexStepOne.class);
		
		job.setMapperClass(StepOneMapper.class);
		job.setReducerClass(StepOneReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		//���һ�²�����ָ����·���Ƿ��Ѵ��ڣ�������ڣ���ɾ��
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(output)){
			
			fs.delete(output, true);
		}
		
		FileOutputFormat.setOutputPath(job, output);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
	
}