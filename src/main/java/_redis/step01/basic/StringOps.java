package _redis.step01.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class StringOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();

		String key = "redis_str";
		// 判断key是否存在
		boolean exists = jedis.exists(key);
		if (exists) {
			// 删除key, 返回实际删除的行数
			long l = jedis.del(key);
			print("del " + key + "=" + l);
		}
		// 新增key, 成功返回 OK
		String result = jedis.set(key, "This is a String demo");
		print("set " + key + "=" + result);
		print("strlen(" + key + ")=" + jedis.strlen(key));
		// 读取key
		String value = jedis.get(key);
		print("get " + key + "=" + value);

		// 设置过期时间为5秒
		jedis.expire(key, 5);
		// 查看某个key的剩余生存时间,单位秒. 永久生存或者不存在的都返回-1
		Long ttl = jedis.ttl(key);
		print("ttl of " + key + "=" + ttl);
		// 移除某个key的生存时间
		jedis.persist(key);
		// 查看key所储存的值的类型
		String type = jedis.type(key);
		print("type of " + key + "=" + type);

		// 一次性新增多个key
		jedis.mset("key201", "value201", "key202", "value202", "key203",
				"value203", "key204", "value204");
		print("批量设置key: key201,key202,key203,key204");
		
		// 读取所有key, 遍历, 判断类型
		System.out.println("读取所有key并筛选string类型");
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			key = it.next();
			if (jedis.type(key).equals("string"))
				System.out.println("get " + key + "=" + jedis.get(key));
		}
		System.out.println("------------------------------------------------------");
		System.out.println();
		
		// 一次性获取多个key值
		print("批量读取key: key201,key202,key203,key204");
		List<String> list = jedis.mget("key201", "key202", "key203", "key204");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		// 一次性删除多个key
		print("批量删除key: key201,key202");
		jedis.del(new String[] { "key201", "key202" });

		// 禁止覆盖
		Long setnx = jedis.setnx("key203", "value203_new");
		print("禁止覆盖setnx key203 to value203_new=" + setnx + "; value=" + jedis.get("key203"));

		// 设置有效期(秒)
		jedis.setex(key, 3, "setex demo");
		print("setex " + key + ",ttl 3=" + jedis.get(key));
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
		print("4秒之后  " + key + "=" + jedis.get(key));
		
		// 获取原值, 更新为新值一步完成
		key = "key203";
		String former = jedis.getSet(key, "value302-after-getset");
		print("getSet 原值:" + former + ";新值" + jedis.get(key));

		// incr 自增
		key = "redis_num";
		jedis.del(key);
		long incr = jedis.incr(key);
		print("incr " + key + " = " + incr);
		incr = jedis.incr(key);
		print("incr " + key + " = " + incr);
		incr = jedis.incrBy(key, 100);
		print("incrBy " + key + " 100 = " + incr);
		Long decr = jedis.decrBy(key, 100);
		print("decrBy " + key + " 100 = " + decr);
		

		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
