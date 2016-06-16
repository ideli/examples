package _redis.step02.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;

import _redis.step01.basic.JedisProvider;
import redis.clients.jedis.Jedis;

public class HashLession {

	public static void main(String[] args) {
		HashLession hl = new HashLession();
		hl.clear();
		
		//添加一批商品
		for(int i = 0; i< 41; i++) {
			Goods goods = new Goods(0, "goods" + String.format("%05d", i), i);
			hl.addGoods(goods);
		}
		//读取商品总数
		System.out.println("商品总数: " + hl.getTotalCount());
		//修改商品价格
		for(int i = 1; i <= hl.getTotalCount(); i++) {
			hl.updatePrice(i, new Random().nextFloat());
		}
		//分页显示
		List<Goods> list = hl.getGoodsList(2, 20);
		System.out.println("第二页商品:");
		for(Goods goods : list) {
			System.out.println(goods);
		}
	}
	
	private Jedis jedis = null;
	
	public HashLession() {
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
		return jedis.hmset(key, map).equals("OK");
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
	 * 读取商品总数
	 * @return
	 */
	public long getTotalCount() {
		String key = "goods:count";
		return NumberUtils.toLong(jedis.get(key));
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
		int to = from + pageSize;
		if(to > totals) {
			to = totals;
		}
		List<Goods> goodsList = new ArrayList<>();
		for(int i = from; i < to; i++) {
			String key = "goods:" + (i + 1);
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
