package _redis.step01.basic;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * 有序集合, 不可重复, 可按权重排序、取值等
 * @author Administrator
 *
 */
public class SortedSetOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();

		// 向sets集合中加入元素, 成功返回1, 失败返回0
		Long zadd = jedis.zadd("zsets", 7.0, "element001");
		print("zadd element001=" + zadd);
		zadd = jedis.zadd("zsets", 8.0, "element001");
		print("zadd element001 again=" + zadd);
		jedis.zadd("zsets", 8.0, "element002");
		jedis.zadd("zsets", 2.0, "element003");
		jedis.zadd("zsets", 3.0, "element004");
		jedis.zadd("zsets", 5.0, "element005");

		// 按权重排序
		Set<String> set = jedis.zrange("zsets", 0, -1);
		print("zrange [0,-1]=" + set);
		
		System.out.println("zrangeWithScores");
		Set<Tuple> tuples = jedis.zrangeWithScores("zsets", 0, -1);
		for(Tuple t : tuples) {
			System.out.println(t.getElement() + ":" + t.getScore());
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// 删除元素
		print("zrem=" + jedis.zrem("zsets", "element005"));
		// 统计元素个数
		print("zcard=" + jedis.zcard("zsets"));
		// 统计某个权重范围内元素个数
		print("zcount [1.0,5.0]=" + jedis.zcount("zsets", 1.0, 5.0));
		
		// 查看集合中element004的权重
		double zscore = jedis.zscore("zsets", "element004");
		print("zscore element004=" + zscore);
		
		// 按权重从第二名开始读取两个元素
		Set<String> zrange = jedis.zrange("zsets", 1, 2);
		print("zrange [1,2]=" + zrange);
		
		print("zincrby -2 element002=" + jedis.zincrby("zsets", -2, "element002"));
		print("zincrby 5.0 element005=" + jedis.zincrby("zsets", 5.0, "element005"));
		
		// 取权重范围内的元素
		zrange = jedis.zrangeByScore("zsets", 3.0, 7.0);
		print("zrangeByScore [3.0,7.0]=" + zrange);
		
		// 按权重反向
		Set<String> zrevrange = jedis.zrevrange("zsets", 1, 2);
		print("zrevrange [1,2]=" + zrevrange);

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
