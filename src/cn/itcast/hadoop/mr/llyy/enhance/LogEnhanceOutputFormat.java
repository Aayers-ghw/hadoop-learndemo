package cn.itcast.hadoop.mr.llyy.enhance;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogEnhanceOutputFormat<K, V> extends FileOutputFormat<K, V> {

	@Override
	public RecordWriter<K, V> getRecordWriter(TaskAttemptContext job)
			throws IOException, InterruptedException {
		
		FileSystem fs = FileSystem.get(new Configuration());
		FSDataOutputStream enhancedOs = fs.create(new Path("/liuliang/output/enhancedLog"));
		FSDataOutputStream tocrawlOs = fs.create(new Path("/liuliang/output/tocrawl"));
		
		return new LogEnhanceRecordWriter<K, V>(enhancedOs,tocrawlOs);
	}

	
	public static class LogEnhanceRecordWriter<K, V> extends RecordWriter<K, V>{
		private FSDataOutputStream enhancedOs =null;
		private FSDataOutputStream tocrawlOs =null;

		public LogEnhanceRecordWriter(FSDataOutputStream enhancedOs,FSDataOutputStream tocrawlOs){
			
			this.enhancedOs = enhancedOs;
			this.tocrawlOs = tocrawlOs;
			
		}
		
		@Override
		public void write(K key, V value) throws IOException,
				InterruptedException {

			if(key.toString().contains("tocrawl")){
				tocrawlOs.write(key.toString().getBytes());
			}else{
				enhancedOs.write(key.toString().getBytes());
			}
			
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException,
				InterruptedException {

			if(enhancedOs != null){
				enhancedOs.close();
			}
			if(tocrawlOs != null){
				tocrawlOs.close();
			}
			
		}
		
	}
	
}
