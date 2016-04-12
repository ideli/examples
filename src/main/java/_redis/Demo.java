package _redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.Serializable;

import _basic.utils.Serializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

public class Demo {
	
	public void setAndGet() {
		
		String operate = "key";
		
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			
			switch(operate) {
			case "flush":
				//清空数据库
				jedis.flushDB();
				break;
			case "key":
				//键操作
				KeyOperate(jedis);
				break;
			case "string":
				//字符串操作
				StringOperate(jedis);
				break;
			case "list":
				//List操作
				ListOperate(jedis);
				break;
			case "set":
				//Set操作
				SetOperate(jedis);
				break;
			case "storedset":
				//SortedSet操作
				SortedSetOperate(jedis);
				break;
			case "hash":
				//Hash操作
				HashOperate(jedis);
				break;
			case "object":
				//对象操作
				ObjectOperate(jedis);
				break;
			}
			
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void KeyOperate(Jedis jedis) {
		//字符串操作
		String stringKey = "com.notes.cache.redis.demoKey";
		//判断key是否存在
		boolean exists = jedis.exists(stringKey);
		if(exists) {
			//删除key
			long l = jedis.del(stringKey);
			System.out.println("jedis.del:" + l);
		}
		//新增key
		String result = jedis.set(stringKey, "This is a String demo");
		System.out.println("jedis.set:" + result);
		//读取key
		String value = jedis.get(stringKey);
		System.out.println(stringKey + ":" + value);
		//读取所有key
		Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            String key = it.next();
            if(jedis.type(key).equals("string"))
            	System.out.println(key + ":" + jedis.get(key));
        }
        //设置过期时间为5秒
        jedis.expire(stringKey, 5);
        //查看某个key的剩余生存时间,单位秒. 永久生存或者不存在的都返回-1
        Long ttl = jedis.ttl(stringKey);
        System.out.println(stringKey + "的剩余生存时间：" + ttl);
        //移除某个key的生存时间
        jedis.persist(stringKey);
        //查看key所储存的值的类型
        String type = jedis.type(stringKey);
        System.out.println(stringKey + " Type：" + type);
	}
	
	public void StringOperate(Jedis jedis) {
		//字符串操作
		String stringKey = "com.notes.cache.redis.stringKey";
		//普通增删查操作同KeyOperate
		
		//一次性新增多个key
		jedis.mset("key201", "value201", "key202", "value202", "key203", "value203", "key204", "value204");
		//一次性获取多个key值
		List<String> list = jedis.mget("key201", "key202", "key203", "key204");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		//一次性删除多个key
		jedis.del(new String[]{"key201", "key202"});
		
		//禁止覆盖
		Long setnx = jedis.setnx("key203", "value203_new");
		System.out.println("setnx return " + setnx + ", new value=" + jedis.get("key203"));
		
		//设置有效期(秒)
		jedis.setex(stringKey, 3, "setex demo");
		System.out.println(stringKey + ":" + jedis.get(stringKey));
		try{
            Thread.sleep(4000);
        } catch (InterruptedException e){
        }
		System.out.println("4秒之后  " + stringKey + ":" + jedis.get(stringKey));
		//获取原值, 更新为新值一步完成
		jedis.getSet(stringKey, "value302-after-getset");
	}
	
	//链表, 可重复, 可做队列
	public void ListOperate(Jedis jedis) {
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "ArrayList");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "LinkedList");
		jedis.lpush("stringlists", "MapList");
		jedis.lpush("stringlists", "SerialList");
		jedis.lpush("stringlists", "HashList");
		jedis.lpush("numberlists", "3");
		jedis.lpush("numberlists", "1");
		jedis.lpush("numberlists", "5");
		jedis.lpush("numberlists", "2");
		System.out.println("list stringlists：" + jedis.lrange("stringlists", 0, -1));
        System.out.println("list numberlists：" + jedis.lrange("numberlists", 0, -1));
        
        //删除列表指定的值,第二个参数为删除的个数(有重复时),后add进去的值先被删,类似于出栈. 返回删除个数
        Long del = jedis.lrem("stringlists", 2, "vector");
        System.out.println("删除" + del + "个元素, 删除后 list stringlists：" + jedis.lrange("stringlists", 0, -1));
        //删除区间以外的数据, 成功返回 OK
        String ltrim = jedis.ltrim("stringlists", 0, 3);
        System.out.println("删除0-3区间外元素, 返回  " + ltrim + ", 删除后 list stringlists：" + jedis.lrange("stringlists", 0, -1));
        
        //列表元素出栈
        String lpop = jedis.lpop("stringlists");
        System.out.println("出栈元素: " + lpop + "出栈后 list stringlists：" + jedis.lrange("stringlists", 0, -1));
        
        //修改列表中指定下标的值 
        jedis.lset("stringlists", 0, "hello list!");
        
        //数组长度
        jedis.llen("stringlists");
        
        //排序
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.alpha();
        sortingParameters.limit(0, 3);
        List<String> list = jedis.sort("stringlists", sortingParameters);
        String sort = "";
		for(int i = 0; i < list.size(); i++) {
			sort += list.get(i) + " ";
		}
		System.out.println("排序结果: " + sort);
		
		//获取列表指定下标的值, 不存在返回null
		int pos = 2;
		String lindex = jedis.lindex("stringlists", pos);
		System.out.println("下标为" + pos + "的值: " + lindex);
	}
	
	//不重复集合, 可求交集、并集运算, 如取共同好友等
	public void SetOperate(Jedis jedis) {
		//向sets集合中加入元素, 成功返回1, 失败返回0
		Long sadd = jedis.sadd("sets", "element001");
		System.out.println("向sets集合中加入元素element001: " + sadd);
		sadd = jedis.sadd("sets", "element001");
		System.out.println("向sets集合中重复加入元素element001: " + sadd);
        jedis.sadd("sets", "element002");
        jedis.sadd("sets", "element003");
        jedis.sadd("sets", "element004");
        Set<String> set = jedis.smembers("sets");
        String setall = "";
        Iterator<String> i = set.iterator();
        while(i.hasNext()) {
        	setall += i.next() + " ";
        }
        System.out.println("查看sets集合中的所有元素: " + setall);
        
        //删除元素
        jedis.srem("sets", "element003");
        //判断是否存在元素
        jedis.sismember("sets", "element001");
        
        jedis.sadd("sets1", "element003");
        jedis.sadd("sets1", "element004");
        jedis.sadd("sets1", "element005");
        jedis.sadd("sets1", "element006");
        //交集
        set = jedis.sinter("sets", "sets1");
        System.out.println("交集: " + set);
        //并集
        set = jedis.sunion("sets", "sets1");
        System.out.println("并集: " + set);
        //差集(set中有, set1中没有的元素)
        set = jedis.sdiff("sets", "sets1");
        System.out.println("差集: " + set);
	}
	
	//有序集合, 不可重复, 可按权重排序、取值等
	public void SortedSetOperate(Jedis jedis) {
		// 向sets集合中加入元素, 成功返回1, 失败返回0
		Long zadd = jedis.zadd("zsets", 7.0, "element001");
		System.out.println("向zsets集合中加入元素element001: " + zadd);
		zadd = jedis.zadd("zsets", 8.0, "element001");
		System.out.println("向zsets集合中重复加入元素element001: " + zadd);
		jedis.zadd("zsets", 8.0, "element002");
		jedis.zadd("zsets", 2.0, "element003");
		jedis.zadd("zsets", 3.0, "element004");
		
		//按权重排序
		Set<String> set = jedis.zrange("zsets", 0, -1);
		String setall = "";
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			setall += i.next() + " ";
		}
		System.out.println("按权重排序: " + setall);
		
		//删除元素
		jedis.zrem("zsets", "element002");
		//统计元素个数
		jedis.zcard("zsets");
		//统计某个权重范围内元素个数
		jedis.zcount("zsets", 1.0, 5.0);
		//查看集合中element004的权重
		jedis.zscore("zsets", "element004");
		//获取下标1-2范围内的元素
		jedis.zrange("zsets", 1, 2);
	}
	
	//存储对象, 可单独更新一个键值
	public void HashOperate(Jedis jedis) {

		Long hset = jedis.hset("hashs", "key001", "value001");
		System.out.println("hashs中添加key001和value001键值对: " + hset);
		hset = jedis.hset("hashs", "key001", "value001");
		System.out.println("hashs中添加重复key001和value001键值对: " + hset);
		jedis.hset("hashs", "key002", "value002");
		jedis.hset("hashs", "key003", "value003");
		//新增key004和4的整型键值对
		jedis.hincrBy("hashs", "key004", 4l);
		
		List<String> list = jedis.hvals("hashs");
		String hvals = "";
		for(int i = 0; i < list.size(); i++) {
			hvals += list.get(i) + " ";
		}
		System.out.println("hashs中的所有值: " + hvals);
		
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
		
		list = jedis.hvals("hashs");
		hvals = "";
		for(int i = 0; i < list.size(); i++) {
			hvals += list.get(i) + " ";
		}
		System.out.println("hashs中的所有值: " + hvals);
	}
	
	public void ObjectOperate(Jedis jedis) {
		String personKey = "person";
		Persion p = new Persion();
		p.setName("son");
		p.setAge(18);
		HashMap<String, String> parents = new HashMap<String, String>();
		parents.put("father", "father");
		parents.put("mother", "mother");
		p.setParents(parents);
		jedis.del(personKey.getBytes());
		jedis.set(personKey.getBytes(), Serializer.serialize(p));
		byte[] data = jedis.get(personKey.getBytes());
		Persion newP = (Persion)Serializer.unserialize(data);
		System.out.println(newP.ToString());
	}

	private static JedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
		config.setMaxIdle(200);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		pool = new JedisPool(config, "210.14.138.151", 6379);
	}
}

class Persion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String strName;
	private int iAge;
	private HashMap<String, String> parents;
	
	public String getName() {
		return this.strName;
	}
	
	public void setName(String strName) {
		this.strName = strName;
	}
	
	public int getAge() {
		return this.iAge;
	}
	
	public void setAge(int iAge) {
		this.iAge = iAge;
	}
	
	public HashMap<String, String> getParents() {
		return this.parents;
	}
	
	public void setParents(HashMap<String, String> parents) {
		this.parents = parents;
	}
	
	public String ToString() {
		String strFather = "";
		String strMother = "";
		if(parents.containsKey("father")) {
			strFather = parents.get("father");
		}
		if(parents.containsKey("mother")) {
			strMother = parents.get("mother");
		}
		return "{\"name\":\"" + this.strName + "\",\"age\":\"" + iAge + "\",\"father\":\"" + strFather + "\",\"mother\":\"" + strMother + "\"}";
	}
}
