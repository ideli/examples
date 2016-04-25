package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.PushOptions;

import static com.mongodb.client.model.Updates.*;

public class LearnExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		LearnExamples client = new LearnExamples(database);
		client.insertAndUpdate();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public LearnExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void insertAndUpdate() {
		database.getCollection("language").drop();
		
		//单条插入文档
		Document doc = new Document("oop", "java");
		database.getCollection("language").insertOne(doc);
		doc = new Document("oop", "csharp").append("copyright", "microsoft");
		database.getCollection("language").insertOne(doc);
		//查找并修改一个文档
		database.getCollection("language").findOneAndReplace(new Document("oop", "java"), new Document("oop", "java").append("copyright", "oracle"));
		//删除一个文档
		database.getCollection("language").deleteOne(new Document("oop", "java"));
		//删除全部文档
		database.getCollection("language").deleteMany(new Document());
		
		doc = new Document("oop", "java").append("copyright", "oracle");
		database.getCollection("language").insertOne(doc);
		doc = new Document("oop", "csharp").append("copyright", "microsoft");
		database.getCollection("language").insertOne(doc);
		
		//用$set修改
		Bson bson = set("rank", 100);
		database.getCollection("language").updateOne(new Document("oop", "java"), bson);
		//用$inc修改
		bson = inc("rank", 30);
		database.getCollection("language").updateOne(new Document("oop", "csharp"), bson);
		database.getCollection("language").updateOne(new Document("oop", "csharp"), bson);
		//用$unset修改
		doc = new Document("oop", "swift").append("copyright", "apple");
		database.getCollection("language").insertOne(doc);
		bson = unset("copyright");
		database.getCollection("language").updateMany(new Document(), bson);
		//$mul 相乘
		bson = set("rank", 100);
		database.getCollection("language").updateOne(new Document("oop", "java"), bson);
		bson = mul("rank", 0.2);
		database.getCollection("language").updateOne(new Document("oop", "java"), bson);
		//$rename
		bson = rename("rank", "ranks");
		database.getCollection("language").updateMany(new Document(), bson);
		//$min 取当前值和指定值之间比较小的
		bson = min("ranks", 50);
		database.getCollection("language").updateMany(new Document(), bson);
		//$max 取当前值和指定值之间比较大的
		bson = max("ranks", 50);
		database.getCollection("language").updateMany(new Document(), bson);
		//$currentDate
		bson = currentDate("add");
		database.getCollection("language").updateMany(new Document(), bson);
		//$currentTimestamp
		bson = currentTimestamp("lastModified");
		database.getCollection("language").updateMany(new Document(), bson);
		//$addToSet
		bson = addToSet("keywords", "for");
		database.getCollection("language").updateMany(new Document(), bson);
		//$addEachToSet
		bson = addEachToSet("keywords", Arrays.asList("while", "true", "do", "new", "override"));
		database.getCollection("language").updateMany(new Document(), bson);
		//$popFirst 删除第一个元素
		bson = popFirst("keywords");
		database.getCollection("language").updateMany(new Document(), bson);
		//$popLast 删除最后一个元素
		bson = popLast("keywords");
		database.getCollection("language").updateMany(new Document(), bson);
		//$pull 删除指定元素
		bson = pull("keywords", "new");
		database.getCollection("language").updateMany(new Document(), bson);
		//$pullByFilter
		bson = pullByFilter(Filters.gte("keywords", "true"));
		database.getCollection("language").updateMany(new Document(), bson);
		//$pullAll 删除全部
		bson = pullAll("keywords", Arrays.asList("while", "true", "do", "new", "override"));
		database.getCollection("language").updateMany(new Document(), bson);
		
		//如果存在则不会重复添加
		bson = addEachToSet("keywords", Arrays.asList("while", "true", "do", "new", "override"));
		database.getCollection("language").updateMany(new Document(), bson);
		database.getCollection("language").updateMany(new Document(), bson);
		
		//$push
		bson = push("scores", 89);
		database.getCollection("language").updateMany(new Document(), bson);
		//$pushEach
		bson = pushEach("scores", Arrays.asList(89, 90, 92));
		database.getCollection("language").updateMany(new Document(), bson);
		bson = pushEach("scores", Arrays.asList(11, 12, 13), new PushOptions().position(0));
		database.getCollection("language").updateMany(new Document(), bson);
		//倒序排列
		bson = pushEach("scores", Arrays.asList(40, 41), new PushOptions().sort(-1));
		database.getCollection("language").updateMany(new Document(), bson);
		//倒序排列并只保留前3个
		bson = pushEach("scores", Arrays.asList(60, 61), new PushOptions().sort(-1).slice(3));
		database.getCollection("language").updateMany(new Document(), bson);
		//
		bson = pushEach("experts",
                Arrays.asList(new Document("first", "Rod").append("last", "Johnson"),
                              new Document("first", "Doug").append("last", "Cutting")));
		database.getCollection("language").updateOne(new Document("oop", "java"), bson);
		
		//combine
		bson = combine(set("author", "James Gosling"), set("version", "2016"));
		database.getCollection("language").updateOne(new Document("oop", "java"), bson);
	}
}
