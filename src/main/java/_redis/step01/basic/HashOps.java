package _redis.step01.basic;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * 哈希表, 键值对集合, 可用于存储对象, 可单独更新一个键值
 * @author Administrator
 *
 */
public class HashOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();

		//hset 仅当操作在hash中创建新key时返回1
		Long hset = jedis.hset("hashs", "key001", "value01");
		print("hset key001 value01=" + hset + "; value=" + jedis.hget("hashs", "key001"));
		//key已存在, 执行修改, 返回0
		hset = jedis.hset("hashs", "key001", "value001");
		print("hset key001 value001=" + hset + "; value=" + jedis.hget("hashs", "key001"));
		//hsetex 如果是新key则添加, 如果key已存在不会修改值, 可用来添加要求不重复的key
		hset = jedis.hsetnx("hashs", "key001", "value0001");
		print("hsetnx key001 value0001=" + hset + "; value=" + jedis.hget("hashs", "key001"));
		
		hset = jedis.hset("hashs", "key002", "value002");
		print("hset key002 value002=" + hset + "; value=" + jedis.hget("hashs", "key002"));
		jedis.hsetnx("hashs", "key003", "value003");
		print("hsetnx key003 value003=" + hset + "; value=" + jedis.hget("hashs", "key003"));
		
		//新增key004和4的整型键值对
		long hincr = jedis.hincrBy("hashs", "key004", 4l);
		print("hincrBy key004 4=" + hincr + "; value=" + jedis.hget("hashs", "key004"));
		
		print("hlen=" + jedis.hlen("hashs"));
		
		System.out.println("hgetAll 读取所有键值对");
		Map<String, String> maps = jedis.hgetAll("hashs");
		for(String akey : maps.keySet()) {
			System.out.println("hget " + akey + "=" + maps.get(akey));
		}
		System.out.println("------------------------------------------------------");
		System.out.println();

		
		List<String> list = jedis.hvals("hashs");
		String hvals = "";
		for(int i = 0; i < list.size(); i++) {
			hvals += list.get(i) + " ";
		}
		print("hashs中的所有值: " + hvals);
		
		//删除键key002
		jedis.hdel("hashs", "key002");
		//key004整型键值的值增加100
		jedis.hincrBy("hashs", "key004", 100l);
		//判断key003是否存在
		jedis.hexists("hashs", "key003");
		//获取key004对应的值
		jedis.hget("hashs", "key004");
		//批量获取key001和key003对应的值
		jedis.hmget("hashs", "key001", "key003");
		//获取hashs中所有的key
		jedis.hkeys("hashs");
		//获取hashs中所有的value
		jedis.hvals("hashs");
		
		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
