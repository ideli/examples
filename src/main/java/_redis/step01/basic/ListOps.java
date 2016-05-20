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
		long lpush = jedis.lpush("stringlists", "vector");
		print("lpush stringlists vector=" + lpush);
		lpush = jedis.lpush("stringlists", "ArrayList");
		print("lpush stringlists ArrayList=" + lpush);
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "LinkedList");
		jedis.lpush("stringlists", "MapList");
		jedis.lpush("stringlists", "SerialList");
		jedis.lpush("stringlists", "HashList");
		
		//rpush 在右边添加
		jedis.rpush("numberlists", "5");
		jedis.rpush("numberlists", "4");
		jedis.rpush("numberlists", "2");
		jedis.rpush("numberlists", "1");
		
		//lrange 获得列表片段
		print("lrange stringlists [0,-1]=" + jedis.lrange("stringlists", 0, -1));
		print("lrange numberlists [0,-1]=" + jedis.lrange("numberlists", 0, -1));
		print("lrange stringlists [1,3]=" + jedis.lrange("stringlists", 1, 3));
        
        //删除列表指定的值,第二个参数为删除的个数(有重复时),后add进去的值先被删,类似于出栈,返回删除个数
        Long del = jedis.lrem("stringlists", 2, "vector");
        print("lrem Vector*2=" + del);
        print("after lrem lrange stringlists [0,-1]=" + jedis.lrange("stringlists", 0, -1));
        
        //删除区间以外的数据, 成功返回 OK
        String ltrim = jedis.ltrim("stringlists", 0, 3);
        print("ltrim [0,3]=" + ltrim);
        print("after ltrim lrange stringlists [0,-1]=" + jedis.lrange("stringlists", 0, -1));
        
        //列表元素出栈
        String lpop = jedis.lpop("stringlists");
        print("lpop=" + lpop);
        print("after lpop lrange stringlists [0,-1]=" + jedis.lrange("stringlists", 0, -1));
        
        //修改列表中指定下标的值 
        String lset = jedis.lset("stringlists", 0, "hello list!");
        print("lset 0=" + lset);
        print("after lset lrange stringlists [0,-1]=" + jedis.lrange("stringlists", 0, -1));
        
        //数组长度
        print("llen=" + jedis.llen("stringlists"));
        
        //排序
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.alpha();
        sortingParameters.limit(0, 3);
        List<String> list = jedis.sort("stringlists", sortingParameters);
        String sort = "";
		for(int i = 0; i < list.size(); i++) {
			sort += list.get(i) + " ";
		}
		print("sort=" + sort);
		
		//获取列表指定下标的值, 不存在返回null
		int pos = 2;
		String lindex = jedis.lindex("stringlists", pos);
		print("lindex 2=" + lindex);
		
		jedis.linsert("numberlists", LIST_POSITION.BEFORE, "2", "3");
		print("after linsert lrange numberlists [0,-1]=" + jedis.lrange("numberlists", 0, -1));
		
		String value = jedis.rpoplpush("numberlists", "dst");
		while(value != null) {
			System.out.println("rpoplpush=" + value);
			value = jedis.rpoplpush("numberlists", "dst");
		}
		
		jedis.close();
	}

	private static void print(String info) {
		System.out.println(info);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}
