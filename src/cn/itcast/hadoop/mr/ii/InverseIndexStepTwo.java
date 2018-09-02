package cn.itcast.hadoop.mr.ii;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Reducer;

import cn.itcast.hadoop.mr.ii.InverseIndexStepOne.StepOneMapper;
import cn.itcast.hadoop.mr.ii.InverseIndexStepOne.StepOneReducer;

public class InverseIndexStepTwo {

public static class StepTwoMapper extends Mapper<LongWritable, Text, Text, Text>{
		
		//k:起始偏移量	v：{hello-->a.txt	3}
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			
			String[] fields = StringUtils.split(line, "\t");
			String[] wordAndfileName = StringUtils.split(fields[0], "-->");
			
			String word = wordAndfileName[0];
			String fileName = wordAndfileName[1];
			long count = Long.parseLong(fields[1]);
			
			context.write(new Text(word), new Text(fileName + "-->" + count));
			//map的输出形式：<hello,a.txt-->3>
		}
	}


	public static class StepTwoReducer extends Reducer<Text, Text, Text, Text>{
		
		@Override
		protected void reduce(Text key, Iterable<Text> values,Context context)
				throws IOException, InterruptedException {
			
			//拿到的数据<hello,{a.txt-->3,b.txt-->2,c.txt-->1}>
			String result = "";
			
			for (Text value : values) {
				
				result += value + " ";
			}
			
			context.write(key, new Text(result));
			//输出的结果就是 k: hello		v:a.txt-->3 b.txt-->2 c.txt-->1
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();	
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(InverseIndexStepTwo.class);
		
		job.setMapperClass(StepTwoMapper.class);
		job.setReducerClass(StepTwoReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		//检查一下参数所指定的路径是否已存在，如果存在，先删除
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(output)){
			
			fs.delete(output, true);
		}
		
		FileOutputFormat.setOutputPath(job, output);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
}
