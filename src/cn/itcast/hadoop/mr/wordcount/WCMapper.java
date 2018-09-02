package cn.itcast.hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//�ĸ����ͣ�ǰ������ָ��mapper�������ݵ����ͣ�KEYIN�������key�����ͣ�VALUEIN�������value������
//map��reducer �������������������key-value�Ե���ʽ��װ��
//Ĭ������£���ܴ��ݸ����ǵ�mapper�����������У�key��Ҫ�������ı���һ�е���ʼƫ��������һ�е�������Ϊvalue
public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

	//mapreduce���ÿ��һ�����ݾ͵���һ�θ÷���
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		
		//����ҵ���߼���д����������У���������ҵ��Ҫ�����������Ѿ�����ܴ��ݽ������ڷ����Ĳ�����key-value
		//key ����һ�����ݵ���ʼƫ������value����һ�е��ı�����
		
		//����һ�е�����ת����String����
		String line = value.toString();
		
		//����һ�е��ı����ض��ָ����з�
		String[] words = StringUtils.split(line, " ");
		
		//������������������Ϊkv��ʽ k: ���� v:1
		for (String word : words) {
			
			context.write(new Text(word), new LongWritable(1));
		}
	}
}