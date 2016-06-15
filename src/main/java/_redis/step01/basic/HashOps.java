package _redis.step01.basic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 哈希表, 键值对集合, 可用于存储对象, 可单独更新一个键值
 * 
 * @author Administrator
 *
 */
public class HashOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();
		
		//为了避免混淆,下文中对Hash表中的键统称为field

		String key = "goods";
		
		// hset 仅当操作在hash中创建新field时返回1
		Long hset = jedis.hset(key, "id", "1");
		print("hset id 1=" + hset + "; value=" + jedis.hget(key, "id"));

		// 如果field已存在则执行修改,并返回0
		hset = jedis.hset(key, "id", "2");
		print("hset id 2=" + hset + "; value=" + jedis.hget(key, "id"));
		
		// hexists 判断field是否存在
		boolean hexists = jedis.hexists(key, "id");
		print("hexists id=" + hexists);
		hexists = jedis.hexists(key, "title");
		print("hexists title=" + hexists);

		// hsetex 如果field不存在则添加, 已存在则不会修改值, 可用来添加要求不重复的field
		Long hsetnx = jedis.hsetnx(key, "id", "3");
		print("hsetnx id 3=" + hsetnx + "; value=" + jedis.hget(key, "id"));
		hsetnx = jedis.hsetnx(key, "title", "商品001");
		print("hsetnx title 商品001=" + hsetnx + "; value=" + jedis.hget(key, "title"));

		// hincr 新增整数类型的键值对或增加值
		long hincr = jedis.hincrBy(key, "price", 4l);
		print("hincrBy price 4=" + hincr + "; value=" + jedis.hget(key, "price"));

		// hlen 读取field数量
		print("hlen=" + jedis.hlen(key));
		
		// hkeys 读取所有field
		Set<String> sets = jedis.hkeys(key);
		print("hkeys=" + Arrays.toString(sets.toArray()));

		// hvals 读取所有值
		List<String> list = jedis.hvals(key);
		print("hvals=" + Arrays.toString(list.toArray()));

		// hgetAll 读取所有键值对
		System.out.println("hgetAll 读取所有键值对");
		Map<String, String> maps = jedis.hgetAll(key);
		for (String field : maps.keySet()) {
			System.out.println("hget " + field + "=" + maps.get(field));
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		// hdel 删除field
		Long hdel = jedis.hdel(key, "id");
		print("hdel id=" + hdel);
		
		// 删除多个field
		jedis.hset(key, "field1", "1");
		jedis.hset(key, "field2", "1");
		hdel = jedis.hdel(key, "field1", "field2");
		print("hdel field1,field2=" + hdel);
		
		// hincrBy 在整数类型值上增加, 返回修改后的值
		Long hincrBy = jedis.hincrBy(key, "price", 100l);
		print("hincrBy price 100=" + hincrBy);

		// hget 读取单个field的值
		String hget = jedis.hget(key, "title");
		print("hget title=" + hget);
		
		// hmget 批量读取field的值
		jedis.hmget(key, "title", "price");
		list = jedis.hvals(key);
		print("hmget title,price=" + Arrays.toString(list.toArray()));

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
