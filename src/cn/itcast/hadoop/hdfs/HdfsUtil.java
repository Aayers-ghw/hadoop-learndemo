package cn.itcast.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.mapred.LocatedFileStatusFetcher;
import org.junit.Before;
import org.junit.Test;

public class HdfsUtil {
	
	FileSystem fs = null;

	@Before
	public void init() throws IOException, InterruptedException, URISyntaxException{

		//读取classpath下的xxx-site.zml 配置文件，并解析其内容，封装到conf对象
		Configuration  conf = new Configuration();
		
		//也可以在代码中对conf中的配置信息进行手动设置，会覆盖掉配置文件中的读取的值
//		conf.set("fs.default.name", "hdfs://hadoop-3:9000/");
		
		//根据配置信息，去获取一个具体文件系统的客户端操纵实例对象
		fs = FileSystem.get(new URI("hdfs://hadoop-3:9000/"), conf, "hadoop");
		
	}
	
	/**
	 * 上传文件
	 * @throws IOException 
	 */
//	@Test
	public void upload() throws IOException{
		
		Path dst = new Path("hdfs://hadoop-3:9000/aa/qingshu.txt");
		
		FSDataOutputStream os = fs.create(dst);
		
		FileInputStream is = new FileInputStream("E:/BaiduNetdiskDownload/qingshu.txt");
		
		IOUtils.copy(is, os);
	}
	
//	@Test
	public void upload2() throws IllegalArgumentException, IOException{
		
		fs.copyFromLocalFile(new Path("E:/BaiduNetdiskDownload/qingshu.txt"), new Path("hdfs://hadoop-3:9000/aaa/bbb/ccc/qingshu.txt"));
	}
	
	/**
	 * 下载文件	
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
	public void download() throws IllegalArgumentException, IOException{
		
		fs.copyToLocalFile(new Path("hdfs://hadoop-3:9000/aa/qingshu.txt"), new Path("E:/BaiduNetdiskDownload/qingshu.txt"));
	}
	
	/**
	 * 查看文件信息
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void listFile() throws FileNotFoundException, IllegalArgumentException, IOException{
		
		RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
		
		while(files.hasNext()){
			
			LocatedFileStatus file = files.next();
			Path filePath = file.getPath();
			String fileName = filePath.getName();
			System.out.println(fileName);
		}
		
		System.out.println("==============================");
		
		FileStatus[] listStatus = fs.listStatus(new Path("/"));
		for (FileStatus status : listStatus) {
			
			String name = status.getPath().getName();
			System.out.println(name + (status.isDirectory()?" is dir":" is file"));  
		}
	}
	
	/**
	 * 创建目录
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
	public void mkdir() throws IllegalArgumentException, IOException{
		
		fs.mkdirs(new Path("/aaa/bbb/ccc"));
		
	}
	
	/**
	 * 删除文件或文件夹
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
	public void rm() throws IllegalArgumentException, IOException{
		
		fs.delete(new Path("/aa"), true);
	}
	
	public static void main(String[] args) throws IOException {
		
		Configuration  conf = new Configuration();
		
		conf.set("fs.default.name", "hdfs://hadoop-3:9000/");
		
		FileSystem fs = FileSystem.get(conf);
		
		FSDataInputStream is = fs.open(new Path("/jdk-7u65-linux-i586.tar.gz"));
		
		FileOutputStream os = new FileOutputStream("E:/BaiduNetdiskDownload/jdk7.tgz");
		
		IOUtils.copy(is, os);
	}

}
