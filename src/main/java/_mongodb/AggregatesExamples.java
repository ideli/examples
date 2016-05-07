package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;

public class AggregatesExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		AggregatesExamples client = new AggregatesExamples(database);
		client.beginShow();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public AggregatesExamples(MongoDatabase database) {
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
		
		//类似于select author,sum(words) as totalWords,avg(words) as agerageWords from blog where author='tom' group by author
		AggregateIterable<Document> iterable = mc.aggregate(Arrays.asList(match(eq("author", "tom")),
                group("$author", sum("totalWords", "$words"), avg("averageWords", "$words"), max("maxWords", "$words"), min("minWords", "$words"))));
		printResult("group by author", iterable);
		
		//随机取3个文档, 仅返回title和author字段
		iterable = mc.aggregate(Arrays.asList(sample(3), project(fields(include("title", "author"), excludeId()))));
		printResult("sample(3)", iterable);
		
		//从第2个文档开始取2个文档, 仅返回title和author字段
		iterable = mc.aggregate(Arrays.asList(skip(1), limit(2), project(fields(include("title", "author"), excludeId()))));
		printResult("skip(1), limit(2)", iterable);
		
		//拆分comments为单个文档
		iterable = mc.aggregate(Arrays.asList(match(size("comments", 2)), project(fields(include("comments"), excludeId())), unwind("$comments")));
		printResult("unwind comments", iterable);
		
	}
	
	public void printResult(String doing, AggregateIterable<Document> iterable) {
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
