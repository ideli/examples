package _redis.step01.basic;

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
		Long sadd = jedis.sadd("fruit", "apple");
		print("sadd fruit apple=" + sadd);
		
		//不能添加重复元素
		sadd = jedis.sadd("fruit", "apple");
		print("sadd fruit apple again=" + sadd);
		jedis.sadd("fruit", "banana", "orange", "grape");
		
		// 返回所有元素
		print("smembers fruit=" + jedis.smembers("fruit"));

		// 删除元素
		long srem = jedis.srem("fruit", "apple");
		print("srem fruit apple=" + srem);
		
		// 判断是否存在元素
		boolean exists = jedis.sismember("fruit", "apple");
		print("sismember fruit apple=" + exists);
		exists = jedis.sismember("fruit", "banana");
		print("sismember fruit banana=" + exists);
		
		// 统计元素个数
		print("scard fruit=" + jedis.scard("fruit"));
		
		// 随机获取一个元素
		print("srandmember fruit=" + jedis.srandmember("fruit"));
		
		// 随机弹出一个元素
		print("spop fruit=" + jedis.spop("fruit"));

		jedis.sadd("fruit", "apple", "banana", "orange", "grape");
		jedis.sadd("food", "orange", "grape", "peach", "lemon");
		
		// 求交集
		Set<String> set = jedis.sinter("fruit", "food");
		print("sinter fruit food=" + set);
		
		// 求交集并存储到指定集合
		jedis.sinterstore("sinter", "fruit", "food");
		print("sinterstore sinter=" + jedis.smembers("sinter"));
		
		// 求并集
		set = jedis.sunion("fruit", "food");
		print("sunion fruit food=" + set);
		
		// 求差集(fruit中有, food中没有的元素)
		set = jedis.sdiff("fruit", "food");
		print("sdiff fruit food=" + set);

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
