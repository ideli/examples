package _redis.step02.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import _redis.step01.basic.JedisProvider;
import redis.clients.jedis.Jedis;

public class SortedSetLession {

	public static void main(String[] args) {
		SortedSetLession sl = new SortedSetLession();
		sl.clear();
		
		//添加一批商品
		for(int i = 0; i< 42; i++) {
			Goods goods = new Goods(0, "goods" + String.format("%05d", (i + 1)), i);
			sl.addGoods(goods);
		}
		//读取商品总数
		System.out.println("商品总数: " + sl.getTotalCount());
		//删除第29件商品
		sl.delGoods(29);
		System.out.println("删除后商品总数: " + sl.getTotalCount());
		//分页显示
		List<Goods> list = sl.getGoodsList(2, 20);
		System.out.println("第二页商品:");
		for(Goods goods : list) {
			System.out.println(goods);
		}
		//按分类查找
		List<Goods> listByTag = sl.findGoodsByTag("big", "green");
		System.out.println("所有尺寸为big,颜色为green的商品:");
		for(Goods goods : listByTag) {
			System.out.println(goods);
		}
		//模拟点击商品
		sl.view(3);
		sl.view(3);
		sl.view(4);
		//按点击量排序
		List<Goods> listWithOrder = sl.getGoodsListOrderByViews(1, 20);
		System.out.println("按点击量排序:");
		for(Goods goods : listWithOrder) {
			System.out.println(goods);
		}
	}
	
	private final String[] SIZE = { "small", "middle", "big" };
	private final String[] COLOR = { "red", "yellow", "green" };
	
	private Jedis jedis = null;
	
	public SortedSetLession() {
		this.jedis = JedisProvider.getJedis();
	}

	/**
	 * 获得一个自增主键值
	 * @return
	 */
	private long getIncrementId() {
		String key = "goods:count";
		return jedis.incr(key);
	}
	
	/**
	 * 添加一个商品
	 * @param goods
	 * @return
	 */
	public boolean addGoods(Goods goods) {
		long id = getIncrementId();
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(id));
		map.put("title", goods.getTitle());
		map.put("price", String.valueOf(goods.getPrice()));
		
		//随机获得三个分类
		String size = SIZE[new Random().nextInt(SIZE.length)];
		String color = COLOR[new Random().nextInt(COLOR.length)];
		
		String key = "goods:" + id;
		boolean added = jedis.hmset(key, map).equals("OK");
		if(added) {
			jedis.rpush("goods:list", String.valueOf(id));
			jedis.zadd("goods:views", 0, String.valueOf(id));
			//记录商品所属分类
			jedis.sadd("goods:" + id + ":tags", size, color);
			//记录每个分类下包含的商品ID
			jedis.sadd("tag:" + size + ":goods", String.valueOf(id));
			jedis.sadd("tag:" + color + ":goods", String.valueOf(id));
		}
		return added;
	}
	
	/**
	 * 修改商品标题
	 * @param goods
	 * @return
	 */
	public boolean updateTitle(long id, String title) {
		String key = "goods:" + id;
		return jedis.hset(key, "title", title) == 0;
	}
	
	/**
	 * 修改商品价格
	 * @param id
	 * @param price
	 * @return
	 */
	public boolean updatePrice(long id, float price) {
		String key = "goods:" + id;
		return jedis.hset(key, "price", String.valueOf(price)) == 0;
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	public boolean delGoods(long id) {
		long deleted = jedis.del("goods:" + id);
		if(deleted == 1) {
			jedis.lrem("goods:list", 1, String.valueOf(id));
		}
		return deleted == 1;
	}
	
	/**
	 * 读取商品总数
	 * @return
	 */
	public long getTotalCount() {
		String key = "goods:list";
		return jedis.llen(key);
	}
	
	public void view(long id) {
		jedis.zincrby("goods:views", 1, String.valueOf(id));
	}
	
	/**
	 * 读取用于分页的商品列表
	 * @param pageIndex 页数
	 * @param pageSize 每页显示行数
	 * @return
	 */
	public List<Goods> getGoodsList(int pageIndex, int pageSize) {
		int totals = (int)getTotalCount();
		int from = (pageIndex - 1) * pageSize;
		if(from < 0) {
			from = 0;
		}
		else if(from > totals) {
			from = (totals / pageSize) * pageSize;
		}
		int to = from + pageSize - 1;
		if(to > totals) {
			to = totals;
		}
		List<Goods> goodsList = new ArrayList<>();
		List<String> idList = jedis.lrange("goods:list", from, to);
		for(String id : idList) {
			String key = "goods:" + id;
			Map<String, String> maps = jedis.hgetAll(key);
			Goods goods = new Goods();
			goods.setId(NumberUtils.toLong(maps.get("id")));
			goods.setTitle(maps.get("title"));
			goods.setPrice(NumberUtils.toFloat(maps.get("price")));
			//读取分类
			goods.setTags(jedis.smembers("goods:" + id + ":tags"));
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	public List<Goods> getGoodsListOrderByViews(int pageIndex, int pageSize) {
		int totals = (int)getTotalCount();
		int from = (pageIndex - 1) * pageSize;
		if(from < 0) {
			from = 0;
		}
		else if(from > totals) {
			from = (totals / pageSize) * pageSize;
		}
		int to = from + pageSize - 1;
		if(to > totals) {
			to = totals;
		}
		List<Goods> goodsList = new ArrayList<>();
		Set<String> idList = jedis.zrevrange("goods:views", from, to);
		for(String id : idList) {
			String key = "goods:" + id;
			Map<String, String> maps = jedis.hgetAll(key);
			Goods goods = new Goods();
			goods.setId(NumberUtils.toLong(maps.get("id")));
			goods.setTitle(maps.get("title"));
			goods.setPrice(NumberUtils.toFloat(maps.get("price")));
			//读取分类
			goods.setTags(jedis.smembers("goods:" + id + ":tags"));
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	/**
	 * 查找指定尺寸的商品
	 * @param size
	 * @return
	 */
	public List<Goods> findGoodsBySize(String size) {
		Set<String> ids = jedis.smembers("tag:" + size + ":goods");
		List<Goods> goodsList = new ArrayList<>();
		for(String id : ids) {
			String key = "goods:" + id;
			Map<String, String> maps = jedis.hgetAll(key);
			Goods goods = new Goods();
			goods.setId(NumberUtils.toLong(maps.get("id")));
			goods.setTitle(maps.get("title"));
			goods.setPrice(NumberUtils.toFloat(maps.get("price")));
			//读取分类
			goods.setTags(jedis.smembers("goods:" + id + ":tags"));
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	/**
	 * 查找指定尺寸和颜色的商品
	 * @param size
	 * @param color
	 * @return
	 */
	public List<Goods> findGoodsByTag(String size, String color) {
		Set<String> ids = jedis.sinter("tag:" + size + ":goods", "tag:" + color + ":goods");
		List<Goods> goodsList = new ArrayList<>();
		for(String id : ids) {
			String key = "goods:" + id;
			Map<String, String> maps = jedis.hgetAll(key);
			Goods goods = new Goods();
			goods.setId(NumberUtils.toLong(maps.get("id")));
			goods.setTitle(maps.get("title"));
			goods.setPrice(NumberUtils.toFloat(maps.get("price")));
			//读取分类
			goods.setTags(jedis.smembers("goods:" + id + ":tags"));
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	public void clear() {
		jedis.flushDB();
	}
}
