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

		//��ȡclasspath�µ�xxx-site.zml �����ļ��������������ݣ���װ��conf����
		Configuration  conf = new Configuration();
		
		//Ҳ�����ڴ����ж�conf�е�������Ϣ�����ֶ����ã��Ḳ�ǵ������ļ��еĶ�ȡ��ֵ
//		conf.set("fs.default.name", "hdfs://hadoop-3:9000/");
		
		//����������Ϣ��ȥ��ȡһ�������ļ�ϵͳ�Ŀͻ��˲���ʵ������
		fs = FileSystem.get(new URI("hdfs://hadoop-3:9000/"), conf, "hadoop");
		
	}
	
	/**
	 * �ϴ��ļ�
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
	 * �����ļ�	
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
	public void download() throws IllegalArgumentException, IOException{
		
		fs.copyToLocalFile(new Path("hdfs://hadoop-3:9000/aa/qingshu.txt"), new Path("E:/BaiduNetdiskDownload/qingshu.txt"));
	}
	
	/**
	 * �鿴�ļ���Ϣ
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
	 * ����Ŀ¼
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
	public void mkdir() throws IllegalArgumentException, IOException{
		
		fs.mkdirs(new Path("/aaa/bbb/ccc"));
		
	}
	
	/**
	 * ɾ���ļ����ļ���
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
