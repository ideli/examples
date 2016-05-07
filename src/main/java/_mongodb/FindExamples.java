package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.text;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class FindExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		FindExamples client = new FindExamples(database);
		client.beginShow();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public FindExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void beginShow() {
		MongoCollection<Document> mc = database.getCollection("blog");
		mc.drop();
		
		Document doc1 = new Document("title", "good day").append("author", "tom").append("words", 300)
				.append("comments", Arrays.asList(new Document("author", "joe").append("score", 3).append("comment", "good"), new Document("author", "white").append("score", 1).append("comment", "oh no")));
		Document doc2 = new Document("title", "good").append("author", "john").append("words", 400)
				.append("comments", Arrays.asList(new Document("author", "william").append("score", 4).append("comment", "good"), new Document("author", "white").append("score", 6).append("comment", "very good")));
		Document doc3 = new Document("title", "good night").append("author", "mike").append("words", 200);
		Document doc4 = new Document("title", "happiness").append("author", "tom").append("words", 1480);
		Document doc5 = new Document("title", "a good thing").append("author", "tom").append("words", 180);
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));
		//单字段索引
		mc.createIndex(new Document("words", 1));
		//组合索引
		mc.createIndex(new Document("title", 1).append("author", -1));
		//创建全文索引
		mc.createIndex(new Document("title", "text"));
		
		//查询全部
		FindIterable<Document> iterable = mc.find();
		printResult("find all", iterable);
		
		//查询title=good
        iterable = mc.find(new Document("title", "good"));
        printResult("find title=good", iterable);
        
      //查询title=good and author=tom
        iterable = mc.find(new Document("title", "good").append("author", "tom"));
        printResult("find title=good and author=tom", iterable);
		
		//查询全部按title排序
		iterable = mc.find().sort(ascending("title"));
		printResult("find all ascending title", iterable);
		
		//查询全部按author,title排序
		iterable = mc.find().sort(ascending("author", "title"));
		printResult("find all ascending author,title", iterable);
		
		//查询全部按words倒序排序
		iterable = mc.find().sort(descending("words"));
		printResult("find all descending words", iterable);

        //查询author=tom or words>350
        iterable = mc.find(new Document("$or", Arrays.asList(new Document("author", "tom"), new Document("words", new Document("$gt", 350)))));
        printResult("find author=tom or words>350", iterable);
        
        //返回title和author字段
        iterable = mc.find().projection(include("title", "author"));
        printResult("find all include (title,author)", iterable);
        
        //返回除title外的其他字段
        iterable = mc.find().projection(exclude("title"));
        printResult("find all exclude title", iterable);
        
        //不返回_id字段
        iterable = mc.find().projection(excludeId());
        printResult("find all excludeId", iterable);
        
      //返回title和author字段
        iterable = mc.find().projection(fields(include("title", "author"), excludeId()));
        printResult("find all include (title,author) and excludeId", iterable);
        
        //内嵌文档匹配
        iterable = mc.find(new Document("comments.author", "joe"));
        printResult("find comments.author=joe", iterable);
        
        //一个错误的示例, 想查询评论中包含作者是white且分值>2的, 返回结果不符合预期
        iterable = mc.find(new Document("comments.author", "white").append("comments.score", new Document("$gt", 2)));
        printResult("find comments.author=white and comments.score>2 (wrong)", iterable);
        
        //正确的写法
        iterable = mc.find(elemMatch("comments", Filters.and(Filters.eq("author", "white"), Filters.gt("score", 2))));
        printResult("find comments.author=white and comments.score>2 using elemMatch", iterable);
        
        //查找title以good开头的, 并且comments只保留一个元素
        iterable = mc.find(Filters.regex("title", "^good")).projection(slice("comments", 1));
        printResult("find regex ^good and slice comments 1", iterable);
        
        iterable = mc.find(text("good"));
		printResult("text good", iterable);
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
