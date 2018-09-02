package cn.itcast.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.Server;

public class Starter {

	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		
		Builder builder = new RPC.Builder(new Configuration());
		
		System.setProperty("hadoop.home.dir","E:/Hadoop/hadoop-2.4.1");
		
		builder.setBindAddress("1515182041").setPort(8888).setProtocol(LoginServiceInterface.class).setInstance(new LoginServiceImpl());
		
		Server server = builder.build();
		
		server.start();

	}

}
     