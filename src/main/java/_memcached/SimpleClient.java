package _memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class SimpleClient {

	static {

		String[] servers = { "127.0.0.1:11211", "127.0.0.1:11211"};
		//设置服务器权重
		Integer[] weights = { 3, 2 };
		//创建一个Socked连接池实例
		SockIOPool pool = SockIOPool.getInstance();
		//向连接池设置服务器和权重
		pool.setServers(servers);
		pool.setWeights(weights);

		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		pool.initialize();
	}

	protected static MemCachedClient mcc = new MemCachedClient();
	
	public void setString(String strKey, String strValue) {
		mcc.set(strKey, strValue);
	}
	
	public String getString(String strKey) {
		Object obj = mcc.get(strKey);
		System.out.println(obj);
		if(obj == null)
			return "";
		else
			return obj.toString();
	}
	
	public void setAndGet() {
		String strKey = "thisisakey";
		//setString(strKey, "this is a value xxx");
		System.out.println("Get value from cache: " + getString(strKey));
	}
}
