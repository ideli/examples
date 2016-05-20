package _redis.step01.basic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisProvider {

	private static JedisPool pool;
	
	public static Jedis getJedis() {
		if(pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(1024);
			config.setMaxIdle(200);
			config.setMaxWaitMillis(1000);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			pool = new JedisPool(config, "127.0.0.1", 6379);
		}
		return pool.getResource();
	}
}
