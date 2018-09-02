package cn.itcast.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class LoginController {

	public static void main(String[] args) throws IOException {
		
		LoginServiceInterface proxy = RPC.getProxy(LoginServiceInterface.class, 1L, new InetSocketAddress("1515182041", 8888), new Configuration());

		String result = proxy.login("angelababy", "123456");
		
		System.out.println(result);
	}

}
