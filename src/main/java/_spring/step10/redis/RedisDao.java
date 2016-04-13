package _spring.step10.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisDao {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_spring/step10/redis/spring-redis.xml");
		@SuppressWarnings("unchecked")
		RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>)context.getBean("redisTemplate");
		RedisDao dao = new RedisDao(redisTemplate);
		dao.addOrUpdate("key", "a value");
		System.out.println(dao.load("key"));
		dao.delete("key");
		System.out.println(dao.load("key"));
	}

	protected RedisTemplate<String, String> redisTemplate;
	
	public RedisDao(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public void addOrUpdate(String key, String value) {
		this.redisTemplate.opsForValue().set(key, value);
	}
	
	public String load(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}
	
	public void delete(String key) {
		this.redisTemplate.delete(key);
	}
}
