package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		MongoCollection<Document> mc = database.getCollection("fruits");
		mc.drop();
		
		Document doc1 = new Document("name", "apple").append("color", "red").append("price", 10.0);
		Document doc2 = new Document("name", "orange").append("color", "orange").append("price", 7.99);
		Document doc3 = new Document("name", "banana").append("color", "yellow").append("price", 2.99);
		Document doc4 = new Document("name", "strawberry").append("color", "red").append("price", 14.99);
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4));
		
		mc.createIndex(new Document("price", 1));
		mc.createIndex(new Document("color", 1).append("name", -1));
		
		FindIterable<Document> iterable = mc.find();
		printResult("find all", iterable);
        
        iterable = mc.find(new Document("color", "red"));
        printResult("find color:red", iterable);
        
        iterable = mc.find(new Document("color", "red").append("name", "strawberry"));
        printResult("find color:red and name:strawberry", iterable);
        
        System.out.println("find(new Document(\"$or\", Arrays.asList(new Document(\"color\", \"red\"), new Document(\"price\", new Document(\"$gt\", 8)))))-----------------------");
        iterable = mc.find(new Document("$or", Arrays.asList(new Document("color", "red"), new Document("price", new Document("$gt", 8)))));
        printResult("find color:red or price>8", iterable);
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
