package _redis.step01.basic;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * 有序集合, 不可重复, 可按权重排序、取值等
 * 
 * @author Administrator
 *
 */
public class SortedSetOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();

		// 向有序集合中加入元素, 成功返回1, 失败返回0
		Long zadd = jedis.zadd("fruit", 5.0, "apple");
		print("zadd fruit apple=" + zadd);
		// 重复添加, 会修改权重
		zadd = jedis.zadd("fruit", 6.0, "apple");
		print("zadd fruit apple again=" + zadd);

		jedis.zadd("fruit", 2.0, "banana");
		jedis.zadd("fruit", 4.0, "orange");
		jedis.zadd("fruit", 8.0, "grape");
		jedis.zadd("fruit", 10.0, "lemon");
		jedis.zadd("fruit", 7.0, "cherry");
		
		// 统计元素个数
		print("zcard fruit=" + jedis.zcard("fruit"));
		
		// 统计某个权重范围内元素个数
		print("zcount fruit [1.0,5.0]=" + jedis.zcount("fruit", 1.0, 5.0));
		
		// 查看排名
		print("zrank fruit grape=" + jedis.zrank("fruit", "grape"));
		print("zrevrank fruit grape=" + jedis.zrevrank("fruit", "grape"));

		// 按权重排序后读取索引范围内元素及权重
		System.out.println("zrangeWithScores fruit");
		Set<Tuple> tuples = jedis.zrangeWithScores("fruit", 0, -1);
		for (Tuple t : tuples) {
			System.out.println(t.getElement() + ":" + t.getScore());
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// 按权重反向排序后读取索引范围内元素及权重
		System.out.println("zrevrangeWithScores fruit");
		tuples = jedis.zrevrangeWithScores("fruit", 0, -1);
		for (Tuple t : tuples) {
			System.out.println(t.getElement() + ":" + t.getScore());
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// 按权重排序后读取索引范围内元素
		Set<String> set = jedis.zrange("fruit", 1, 3);
		print("zrange fruit [1,3]=" + set);

		// 按权重反向排序后读取索引范围内元素
		set = jedis.zrevrange("fruit", 1, 3);
		print("zrevrange fruit [1,3]=" + set);

		// 读取权重在指定范围内的元素及其权重并按权重排序
		System.out.println("zrangeByScoreWithScores fruit [1.0,8.0]");
		tuples = jedis.zrangeByScoreWithScores("fruit", 1.0, 8.0);
		for (Tuple t : tuples) {
			System.out.println(t.getElement() + ":" + t.getScore());
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// 读取权重在指定范围内的元素及其权重并按权重反向排序
		System.out.println("zrevrangeByScoreWithScores fruit [8.0,1.0]");
		tuples = jedis.zrevrangeByScoreWithScores("fruit", 8.0, 1.0);
		for (Tuple t : tuples) {
			System.out.println(t.getElement() + ":" + t.getScore());
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// 读取权重在指定范围内的元素并按权重排序
		set = jedis.zrangeByScore("fruit", 1.0, 7.0);
		print("zrangeByScore fruit [1.0,7.0]=" + set);

		// 读取权重在指定范围内的元素并按权重反向排序
		set = jedis.zrevrangeByScore("fruit", 7.0, 1.0);
		print("zrevrangeByScore fruit [7.0,1.0]=" + set);

		// 查看集合中指定元素的权重
		double zscore = jedis.zscore("fruit", "grape");
		print("zscore fruit grape=" + zscore);

		// 修改指定元素权重
		print("zincrby fruit -2 grape=" + jedis.zincrby("fruit", -2, "grape"));
		print("zincrby fruit 5.0 lemon=" + jedis.zincrby("fruit", 5.0, "lemon"));
		print("after zincrby: zscore fruit grape=" + jedis.zscore("fruit", "grape"));
		
		// 删除元素
		print("zrem fruit cherry=" + jedis.zrem("fruit", "cherry"));
		print("zremrangeByScore fruit [3.0,6.0]=" + jedis.zremrangeByScore("fruit", 3.0, 6.0));

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
