package _redis.step01.basic;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransactionOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();

		Transaction tran1 = jedis.multi();
		tran1.incrBy("key1", 10);
		tran1.incrBy("key2", 100);
		tran1.incrBy("key1", 1);
		tran1.incrBy("key2", 10);
		List<Object> results = tran1.exec();
		for(Object o : results) {
			System.out.println(o);
		}
		System.out.println(jedis.get("key1"));
		System.out.println(jedis.get("key2"));
		
		Transaction tran2 = jedis.multi();
		tran2.incrBy("key3", 10);
		tran2.incrBy("key4", 100);
		tran2.incrBy("key3", 1);
		tran2.incrBy("key4", 10);
		tran2.discard();
		System.out.println(jedis.get("key3"));
		System.out.println(jedis.get("key4"));
		
		jedis.incr("key7");
		try {
			Transaction tran3 = jedis.multi();
			tran3.watch("key7");
			tran3.incrBy("key5", 10);
			tran3.incrBy("key6", 100);
			tran3.incrBy("key5", 1);
			tran3.incrBy("key6", 10);
			tran3.incr("key7");
			//如果其他客户端改变了key7则执行失败
			results = tran3.exec();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		for(Object o : results) {
			System.out.println(o);
		}
		System.out.println(jedis.get("key5"));
		System.out.println(jedis.get("key6"));
		
		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
