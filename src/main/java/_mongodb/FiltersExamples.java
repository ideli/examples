package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

public class FiltersExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		FiltersExamples client = new FiltersExamples(database);
		client.beginShow();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public FiltersExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void beginShow() {
		MongoCollection<Document> mc = database.getCollection("fruits");
		mc.drop();
		
		Document doc1 = new Document("name", "apple").append("color", "red").append("price", 3.0).append("size", Arrays.asList(1, 2, 3, 4));
		Document doc2 = new Document("name", "orange").append("color", "orange").append("price", 7.99).append("size", Arrays.asList(2, 3, 4, 5));
		Document doc3 = new Document("name", "banana").append("color", "yellow").append("price", 2.99).append("size", Arrays.asList(3, 4));
		Document doc4 = new Document("name", "strawberry").append("color", "red").append("price", 14.99).append("size", Arrays.asList(4, 5, 6, 7));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4));
		
		//$or $in $nin
        //$not
        //$lt $lte $gt $gte $ne
		FindIterable<Document> iterable = mc.find(eq("color", "red"));
		printResult("Filters color eq red", iterable);
		
		iterable = mc.find(in("color", "orange", "yellow"));
		printResult("Filters color in orange,yellow", iterable);
		
		iterable = mc.find(nin("color", "orange", "yellow"));
		printResult("Filters color nin orange,yellow", iterable);
		
		iterable = mc.find(ne("price", 7.99));
		printResult("Filters price ne 7.99", iterable);
		
		iterable = mc.find(and(eq("color", "red"), gt("price", 8)));
		printResult("Filters color eq red and price gt 8", iterable);
		
		iterable = mc.find(and(or(eq("price", 3.0), eq("price", 7.99)), or(eq("color", "yellow"), lt("price", 14))));
		printResult("Filters (price=3.0 or price=7.99) and (color=yellow or price<14)", iterable);
		
		iterable = mc.find(all("size", Arrays.asList(2, 3, 4)));
		printResult("Filters size all (2, 3, 4)", iterable);
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
