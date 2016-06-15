package _redis.step01.basic;

import java.util.List;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * 链表类型操作(可重复, 可做队列)
 * @author Administrator
 *
 */
public class ListOps {

	public static void main(String[] args) {
		Jedis jedis = JedisProvider.getJedis();
		jedis.flushDB();
		
		//lpush 在左边添加
		String fruitKey = "fruit";
		long lpush = jedis.lpush(fruitKey, "apple");
		print("lpush " + fruitKey + " apple=" + lpush);
		lpush = jedis.lpush(fruitKey, "banana");
		print("lpush " + fruitKey + " banana=" + lpush);
		jedis.lpush(fruitKey, "apple");
		jedis.lpush(fruitKey, "apple");
		jedis.lpush(fruitKey, "orange");
		jedis.lpush(fruitKey, "grape");
		jedis.lpush(fruitKey, "peach");
		jedis.lpush(fruitKey, "lemon");
		
		//rpush 在右边添加
		String numberKey = "numbers";
		jedis.rpush(numberKey, "5");
		jedis.rpush(numberKey, "4");
		jedis.rpush(numberKey, "2");
		jedis.rpush(numberKey, "1");
		
		//lrange 获得列表片段
		print("lrange " + fruitKey + " [0,-1]=" + jedis.lrange(fruitKey, 0, -1));
		print("lrange " + numberKey + " [0,-1]=" + jedis.lrange(numberKey, 0, -1));
		print("lrange " + fruitKey + " [1,3]=" + jedis.lrange(fruitKey, 1, 3));
        
        //删除列表指定的值,第二个参数为删除的个数(有重复时),后添加进去的值先被删,类似于出栈,返回删除个数
        Long del = jedis.lrem(fruitKey, 2, "apple");
        print("lrem " + fruitKey + " apple*2=" + del);
        print("after lrem, lrange " + fruitKey + " [0,-1]=" + jedis.lrange(fruitKey, 0, -1));
        
        //删除区间以外的数据, 成功返回 OK
        String ltrim = jedis.ltrim(fruitKey, 0, 3);
        print("ltrim " + fruitKey + " [0,3]=" + ltrim);
        print("after ltrim, lrange " + fruitKey + " [0,-1]=" + jedis.lrange(fruitKey, 0, -1));
        
        //列表元素出栈
        String lpop = jedis.lpop(fruitKey);
        print("lpop " + fruitKey + " =" + lpop);
        print("after lpop, lrange " + fruitKey + " [0,-1]=" + jedis.lrange(fruitKey, 0, -1));
        
        //修改列表中指定下标的值 
        String lset = jedis.lset(fruitKey, 0, "mango");
        print("lset " + fruitKey + " 0=" + lset);
        print("after lset, lrange " + fruitKey + " [0,-1]=" + jedis.lrange(fruitKey, 0, -1));
        
        //数组长度
        print("llen " + fruitKey + " =" + jedis.llen(fruitKey));
        
        //排序
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.alpha();
        sortingParameters.limit(0, 3);
        List<String> list = jedis.sort(fruitKey, sortingParameters);
        String sort = "";
		for(int i = 0; i < list.size(); i++) {
			sort += list.get(i) + " ";
		}
		print("sort " + fruitKey + " =" + sort);
		
		//获取列表指定下标的值, 不存在返回null
		int pos = 2;
		String lindex = jedis.lindex(fruitKey, pos);
		print("lindex " + fruitKey + " 2=" + lindex);
		
		//在指定元素前/后插入
		jedis.linsert(numberKey, LIST_POSITION.BEFORE, "2", "3");
		print("after linsert, lrange " + numberKey + " [0,-1]=" + jedis.lrange(numberKey, 0, -1));
		
		String value = jedis.rpoplpush(numberKey, "dst");
		while(value != null) {
			System.out.println("rpoplpush " + numberKey + " =" + value);
			value = jedis.rpoplpush(numberKey, "dst");
		}
		print("after rpoplpush, lrange dst [0,-1]=" + jedis.lrange("dst", 0, -1));
		
		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
