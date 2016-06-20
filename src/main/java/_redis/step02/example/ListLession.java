package _redis.step02.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import _redis.step01.basic.JedisProvider;
import redis.clients.jedis.Jedis;

public class ListLession {

	public static void main(String[] args) {
		ListLession ll = new ListLession();
		ll.clear();
		
		//添加一批商品
		for(int i = 0; i< 42; i++) {
			Goods goods = new Goods(0, "goods" + String.format("%05d", (i + 1)), i);
			ll.addGoods(goods);
		}
		//读取商品总数
		System.out.println("商品总数: " + ll.getTotalCount());
		//删除第29件商品
		ll.delGoods(29);
		System.out.println("删除后商品总数: " + ll.getTotalCount());
		//分页显示
		List<Goods> list = ll.getGoodsList(2, 20);
		System.out.println("第二页商品:");
		for(Goods goods : list) {
			System.out.println(goods);
		}
	}
	
	private Jedis jedis = null;
	
	public ListLession() {
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
		String key = "goods:" + id;
		boolean added = jedis.hmset(key, map).equals("OK");
		if(added) {
			jedis.rpush("goods:list", String.valueOf(id));
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
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	public void clear() {
		jedis.flushDB();
	}
}
