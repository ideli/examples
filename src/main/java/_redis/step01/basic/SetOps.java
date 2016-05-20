package _redis.step01.basic;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 不重复集合, 可求交集、并集运算, 如取共同好友等
 * 
 * @author Administrator
 *
 */
public class SetOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();

		// 向sets集合中加入元素, 成功返回1, 失败返回0
		Long sadd = jedis.sadd("sets", "element001");
		print("sadd element001=" + sadd);
		
		sadd = jedis.sadd("sets", "element001");
		print("sadd element001 again=" + sadd);
		
		jedis.sadd("sets", "element002");
		jedis.sadd("sets", "element003", "element004");
		
		Set<String> set = jedis.smembers("sets");
		String setall = "";
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			setall += i.next() + " ";
		}
		print("smembers=" + setall);

		// 删除元素
		long srem = jedis.srem("sets", "element001");
		print("srem element001=" + srem);
		
		// 判断是否存在元素
		boolean exists = jedis.sismember("sets", "element001");
		print("sismember element001=" + exists);
		exists = jedis.sismember("sets", "element002");
		print("sismember element002=" + exists);
		
		//统计元素个数
		print("scard=" + jedis.scard("sets"));
		
		//随机获取一个元素
		print("srandmember=" + jedis.srandmember("sets"));
		
		//从左边弹出一个元素
		print("spop=" + jedis.spop("sets"));

		jedis.sadd("sets1", "element003");
		jedis.sadd("sets1", "element004");
		jedis.sadd("sets1", "element005");
		jedis.sadd("sets1", "element006");
		
		// 交集
		set = jedis.sinter("sets", "sets1");
		print("sinter sets sets1=" + set);
		
		long sinterstore = jedis.sinterstore("sinter", "sets", "sets1");
		print("sinterstore=" + sinterstore + "; smembers sinter=" + jedis.smembers("sinter"));
		
		// 并集
		set = jedis.sunion("sets", "sets1");
		print("sunion sets sets1=" + set);
		
		// 差集(set中有, set1中没有的元素)
		set = jedis.sdiff("sets", "sets1");
		print("sdiff sets sets1=" + set);

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
