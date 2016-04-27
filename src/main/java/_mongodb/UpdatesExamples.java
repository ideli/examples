package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.PushOptions;

import static com.mongodb.client.model.Updates.*;

public class UpdatesExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		UpdatesExamples client = new UpdatesExamples(database);
		client.beginShow();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public UpdatesExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void beginShow() {
		MongoCollection<Document> mc = database.getCollection("language");
		//清空集合
		mc.drop();
		printCollection("drop", mc);
		
		//插入单个文档
		Document doc = new Document("oop", "java");
		mc.insertOne(doc);
		printCollection("insert java", mc);
		
		doc = new Document("oop", "csharp").append("copyright", "microsoft");
		mc.insertOne(doc);
		printCollection("insert csharp", mc);
		
		//查找并修改一个文档
		mc.findOneAndReplace(new Document("oop", "java"), new Document("oop", "java").append("copyright", "oracle"));
		printCollection("findAndReplace java", mc);
		
		//删除一个文档
		mc.deleteOne(new Document("oop", "java"));
		printCollection("delete java", mc);
		
		//删除全部文档
		mc.deleteMany(new Document());
		printCollection("delete all", mc);
		
		doc = new Document("oop", "java").append("copyright", "oracle");
		mc.insertOne(doc);
		doc = new Document("oop", "csharp").append("copyright", "microsoft");
		mc.insertOne(doc);
		printCollection("insert java and csharp", mc);
		
		//$set 有则修改,没有则添加
		mc.updateOne(new Document("oop", "java"), set("rank", 100));
		printCollection("$set java rank 100", mc);
		//$inc 有则增加,没有则添加
		mc.updateOne(new Document("oop", "csharp"), inc("rank", 30));
		printCollection("$inc csharp rank 30", mc);
		mc.updateOne(new Document("oop", "csharp"), inc("rank", 31));
		printCollection("$inc csharp rank 31", mc);
		
		//$unset 有则删除
		doc = new Document("oop", "swift").append("copyright", "apple");
		mc.insertOne(doc);
		printCollection("insert swift", mc);
		mc.updateMany(new Document(), unset("copyright"));
		printCollection("unset all copyright", mc);
		
		//$mul 相乘
		mc.updateOne(new Document("oop", "java"), set("rank", 100));
		printCollection("$set java rank: 100", mc);
		
		mc.updateOne(new Document("oop", "java"), mul("rank", 0.2));
		printCollection("$mul java rank: 0.2", mc);
		
		mc.updateOne(new Document("oop", "swift"), set("rank", 100));
		
		//$rename
		mc.updateMany(new Document(), rename("rank", "ranks"));
		printCollection("$rename java rank to ranks", mc);
		
		//$min 取当前值和指定值之间比较小的
		mc.updateMany(new Document(), min("ranks", 50));
		printCollection("$min all ranks: 50", mc);
		
		//$max 取当前值和指定值之间比较大的
		mc.updateMany(new Document(), max("ranks", 40));
		printCollection("$max all ranks: 40", mc);
		
		//$currentDate
		mc.updateMany(new Document("oop", "java"), currentDate("add"));
		printCollection("$currentDate java", mc);
		
		//$currentTimestamp
		mc.updateMany(new Document("oop", "java"), currentTimestamp("lastModified"));
		printCollection("$currentTimestamp java", mc);
		
		//$addToSet
		mc.updateMany(new Document("oop", "java"), addToSet("keywords", "for"));
		printCollection("$addToSet java keywords: for", mc);
		
		//$addEachToSet
		mc.updateMany(new Document("oop", "java"), addEachToSet("keywords", Arrays.asList("while", "true", "do", "new", "override")));
		printCollection("$addEachToSet java keywords: while,true,do,new,override", mc);
		
		//$popFirst 删除第一个元素
		mc.updateMany(new Document("oop", "java"), popFirst("keywords"));
		printCollection("$popFirst java keywords", mc);
		
		//$popLast 删除最后一个元素
		mc.updateMany(new Document("oop", "java"), popLast("keywords"));
		printCollection("$popLast java keywords", mc);
		
		//$pull 删除指定元素
		mc.updateMany(new Document("oop", "java"), pull("keywords", "new"));
		printCollection("$pull java keywords: new", mc);
		
		//$pullByFilter
		mc.updateMany(new Document("oop", "java"), pullByFilter(Filters.gte("keywords", "true")));
		printCollection("$pullByFilter java keywords: true", mc);
		
		//$pullAll 删除全部
		mc.updateMany(new Document("oop", "java"), pullAll("keywords", Arrays.asList("while", "true", "do", "new", "override")));
		printCollection("$pullAll java keywords", mc);
		
		//如果存在则不会重复添加
		mc.updateMany(new Document("oop", "java"), addEachToSet("keywords", Arrays.asList("while", "true", "do", "new", "override")));
		mc.updateMany(new Document("oop", "java"), addEachToSet("keywords", Arrays.asList("while", "true", "do", "new", "override")));
		printCollection("$addEachToSet java keywords: while,true,do,new,override", mc);
		
		//$push
		mc.updateMany(new Document("oop", "java"), push("scores", 89));
		printCollection("$push java scores: 89", mc);
		
		//$pushEach
		mc.updateMany(new Document("oop", "java"), pushEach("scores", Arrays.asList(89, 90, 92)));
		printCollection("$pushEach java scores: 89,90,92", mc);
		
		mc.updateMany(new Document("oop", "java"), pushEach("scores", Arrays.asList(11, 12, 13), new PushOptions().position(0)));
		printCollection("$pushEach java scores: 11,12,13 at position 0", mc);
		
		//倒序排列
		mc.updateMany(new Document("oop", "java"), pushEach("scores", Arrays.asList(40, 41), new PushOptions().sort(-1)));
		printCollection("$pushEach java scores: 40,41 and sort(-1)", mc);
		
		//倒序排列并只保留前3个
		mc.updateMany(new Document("oop", "java"), pushEach("scores", Arrays.asList(60, 61), new PushOptions().sort(-1).slice(3)));
		printCollection("$pushEach java scores: 60,61 and sort(-1) and slice(3)", mc);
		//
		Bson bson = pushEach("experts",
                Arrays.asList(new Document("first", "Rod").append("last", "Johnson"),
                              new Document("first", "Doug").append("last", "Cutting")));
		mc.updateOne(new Document("oop", "java"), bson);
		printCollection("$pushEach", mc);
		
		//combine
		bson = combine(set("author", "James Gosling"), set("version", "8.0"));
		mc.updateOne(new Document("oop", "java"), bson);
		printCollection("$combine", mc);
	}
	
	public void printCollection(String doing, MongoCollection<Document> mc) {
		System.out.println(doing);
		FindIterable<Document> iterable = mc.find();
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        System.out.println("------------------------------------------------------");
        System.out.println();
	}
	
	
}
