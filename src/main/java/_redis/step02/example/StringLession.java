package _redis.step02.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import _redis.step01.basic.JedisProvider;
import redis.clients.jedis.Jedis;

public class StringLession {

	public static void main(String[] args) {
		StringLession sl = new StringLession();
		sl.clear();
		
		//添加一批商品
		for(int i = 0; i< 41; i++) {
			Goods goods = new Goods(0, "goods" + String.format("%05d", i), i);
			sl.addGoods(goods);
		}
		//读取商品总数
		System.out.println("商品总数: " + sl.getTotalCount());
		//分页显示
		List<Goods> list = sl.getGoodsList(2, 20);
		System.out.println("第二页商品:");
		for(Goods goods : list) {
			System.out.println(goods);
		}
	}
	
	private Jedis jedis = null;
	
	public StringLession() {
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
		goods.setId(id);
		String key = "goods:" + id;
		return jedis.set(key, goods.toString()).equals("OK");
	}
	
	/**
	 * 修改商品
	 * @param goods
	 * @return
	 */
	public boolean editGoods(Goods goods) {
		String key = "goods:" + goods.getId();
		if(jedis.exists(key)) {
			return jedis.set(key, goods.toString()).equals("OK");
		}
		return false;
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
		String[] keys = new String[(int)(to - from)];
		for(int i = from; i < to; i++) {
			keys[i - from] = "goods:" + (i + 1);
		}
		List<String> list = jedis.mget(keys);
		List<Goods> goodsList = new ArrayList<>();
		for(String value : list) {
			goodsList.add(Goods.parseJson(value));
		}
		return goodsList;
	}
	
	public void clear() {
		jedis.flushDB();
	}
}
