package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.BsonType;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Filters.size;
import static com.mongodb.client.model.Filters.text;
import static com.mongodb.client.model.Filters.type;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class FindExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		FindExamples client = new FindExamples(database);
		client.show();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public FindExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void show() {

		MongoCollection<Document> mc = database.getCollection("blog");
		mc.drop();
		
		Document doc1 = new Document("title", "good day").append("owner", "tom").append("words", 300)
				.append("comments", Arrays.asList(new Document("author", "joe").append("score", 3).append("comment", "good"), new Document("author", "white").append("score", 1).append("comment", "oh no")));
		Document doc2 = new Document("title", "good").append("owner", "john").append("words", 400)
				.append("comments", Arrays.asList(new Document("author", "william").append("score", 4).append("comment", "good"), new Document("author", "white").append("score", 6).append("comment", "very good")));
		Document doc3 = new Document("title", "good night").append("owner", "mike").append("words", 200)
				.append("tag", Arrays.asList(1, 2, 3, 4));
		Document doc4 = new Document("title", "happiness").append("owner", "tom").append("words", 1480)
				.append("tag", Arrays.asList(2, 3, 4));
		Document doc5 = new Document("title", "a good thing").append("owner", "tom").append("words", 180)
				.append("tag", Arrays.asList(1, 2, 3, 4, 5));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));
		//创建单字段索引
		mc.createIndex(new Document("words", 1));
		//创建组合索引(同样遵循最左前缀原则)
		mc.createIndex(new Document("title", 1).append("owner", -1));
		//创建全文索引
		mc.createIndex(new Document("title", "text"));
		
		//查询全部
		FindIterable<Document> iterable = mc.find();
		printResult("find all", iterable);
		
		//查询title=good
        iterable = mc.find(new Document("title", "good"));
        printResult("find title=good", iterable);
        
        //查询title=good and owner=tom
        iterable = mc.find(new Document("title", "good").append("owner", "tom"));
        printResult("find title=good and owner=tom", iterable);
        
        //查询title like %good% and owner=tom
        iterable = mc.find(and(regex("title", "good"), eq("owner", "tom")));
        printResult("find title like %good% and owner=tom", iterable);
		
		//查询全部按title排序
		iterable = mc.find().sort(ascending("title"));
		printResult("find all and ascending title", iterable);
		
		//查询全部按owner,title排序
		iterable = mc.find().sort(ascending("owner", "title"));
		printResult("find all and ascending owner,title", iterable);
		
		//查询全部按words倒序排序
		iterable = mc.find().sort(descending("words"));
		printResult("find all and descending words", iterable);

        //查询owner=tom or words>350
        iterable = mc.find(new Document("$or", Arrays.asList(new Document("owner", "tom"), new Document("words", new Document("$gt", 350)))));
        printResult("find owner=tom or words>350", iterable);
        
        //返回title和owner字段
        iterable = mc.find().projection(include("title", "owner"));
        printResult("find all include (title,owner)", iterable);
        
        //返回除title外的其他字段
        iterable = mc.find().projection(exclude("title"));
        printResult("find all exclude title", iterable);
        
        //不返回_id字段
        iterable = mc.find().projection(excludeId());
        printResult("find all excludeId", iterable);
        
        //返回title和owner字段且不返回_id字段
        iterable = mc.find().projection(fields(include("title", "owner"), excludeId()));
        printResult("find all include (title,owner) and excludeId", iterable);
        
        //内嵌文档匹配
        iterable = mc.find(new Document("comments.author", "joe"));
        printResult("find comments.author=joe", iterable);
        
        //一个错误的示例, 想查询评论中包含作者是white且分值>2的, 返回结果不符合预期
        iterable = mc.find(new Document("comments.author", "white").append("comments.score", new Document("$gt", 2)));
        printResult("find comments.author=white and comments.score>2 (wrong)", iterable);
        
        //上面的需求正确的写法
        iterable = mc.find(Projections.elemMatch("comments", Filters.and(Filters.eq("author", "white"), Filters.gt("score", 2))));
        printResult("find comments.author=white and comments.score>2 using elemMatch", iterable);
        
        //查找title以good开头的, 并且comments只保留一个元素
        iterable = mc.find(Filters.regex("title", "^good")).projection(slice("comments", 1));
        printResult("find regex ^good and slice comments 1", iterable);
        
        //全文索引查找
        iterable = mc.find(text("good"));
		printResult("text good", iterable);

		//用Filters构建的title=good
		iterable = mc.find(eq("title", "good"));
		printResult("Filters: title eq good", iterable);
		
		//$in 等同于sql的in
		iterable = mc.find(in("owner", "joe", "john", "william"));
		printResult("Filters: owner in joe,john,william", iterable);
		
		//$nin 等同于sql的not in
		iterable = mc.find(nin("owner", "joe", "john", "tom"));
		printResult("Filters: owner nin joe,john,tom", iterable);
		
		//查询内嵌文档
		iterable = mc.find(in("comments.author", "joe", "tom"));
		printResult("Filters: comments.author in joe,tom", iterable);
		
		//$ne 不等于
		iterable = mc.find(ne("words", 300));
		printResult("Filters: words ne 300", iterable);
		
		//$and 组合条件
		iterable = mc.find(and(eq("owner", "tom"), gt("words", 300)));
		printResult("Filters: owner eq tom and words gt 300", iterable);
		
		//较复杂的组合
		iterable = mc.find(and(or(eq("words", 300), eq("words", 400)), or(eq("owner", "joe"), size("comments", 2))));
		printResult("Filters: (words=300 or words=400) and (owner=joe or size(comments)=2)", iterable);
		
		//查询第2个元素值为2的数组
		iterable = mc.find(eq("tag.1", 2));
		printResult("Filters: tag.1 eq 2", iterable);
		
		//查询匹配全部值的数组
		iterable = mc.find(all("tag", Arrays.asList(1, 2, 3, 4)));
		printResult("Filters: tag match all (1, 2, 3, 4)", iterable);
		
		//$exists
		iterable = mc.find(exists("tag"));
		printResult("Filters: exists tag", iterable);
		
		iterable = mc.find(type("words", BsonType.INT32));
		printResult("Filters: type words is int32", iterable);
	}
	
	public void printResult(String doing, FindIterable<Document> iterable) {
		System.out.println(doing);
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        System.out.println("------------------------------------------------------");
        System.out.println();
	}
}
