package cn.itcast.hadoop.mr.llyy.enhance;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 
 * 读入原始日志数据，抽取其中的url，查询规则库，获得该url指向的网页内容的分析结果，追加到原始日志后
 * 
 * @author Aayers-ghw
 * 
 */

// 读入原始数据 （47个字段） 时间戳 ..... destip srcip ... url .. . get 200 ...
// 抽取其中的url查询规则库得到众多的内容识别信息 网站类别，频道类别，主题词，关键词，影片名，主演，导演。。。。
// 将分析结果追加到原始日志后面
// context.write( 时间戳 ..... destip srcip ... url .. . get 200 ...
// 网站类别，频道类别，主题词，关键词，影片名，主演，导演。。。。)
// 如果某条url在规则库中查不到结果，则输出到带爬清单
// context.write( url tocrawl)
public class LogEnhanceMapper extends
		Mapper<LongWritable, Text, Text, NullWritable> {

	private HashMap<String, String> ruleMap = new HashMap<>();

	// setup方法是在mapper task 初始化时被调用一次
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		DBLoader.dbLoader(ruleMap);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String line = value.toString();

		String[] fields = StringUtils.split(line, "\t");
		try {
			if (fields.length > 27 && StringUtils.isNotEmpty(fields[26])
					&& fields[26].startsWith("http")) {
				String url = fields[26];
				String info = ruleMap.get(url);
				String result = "";
				if (info != null) {
					result = line + "\t" + info + "\n\r";
					context.write(new Text(result), NullWritable.get());
				} else {
					result = url + "\t" + "tocrawl" + "\n\r";
					context.write(new Text(result), NullWritable.get());
				}

			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("exception occured in mapper.....");
		}
	}

}
