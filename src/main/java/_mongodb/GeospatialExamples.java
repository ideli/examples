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
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.Position;

import static com.mongodb.client.model.Indexes.*;

public class GeospatialExamples {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("lesson");
		
		GeospatialExamples client = new GeospatialExamples(database);
		client.beginShow();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public GeospatialExamples(MongoDatabase database) {
		this.database = database;
	}
	
	public void beginShow() {
		MongoCollection<Document> mc = database.getCollection("people");
		mc.drop();
		
		Document doc1 = new Document("name", "xiao ming").append("loc1", Arrays.asList(1.1, 1.1)).append("loc2", new Point(new Position(10, 10)));
		Document doc2 = new Document("name", "xiao hong").append("loc1", Arrays.asList(2.1, 2.1)).append("loc2", new Point(new Position(10.1, 10.1)));
		Document doc3 = new Document("name", "da pang").append("loc1", Arrays.asList(3.1, 3.1)).append("loc2", new Point(new Position(10.2, 10.2)));
		Document doc4 = new Document("name", "xiao hu").append("loc1", Arrays.asList(4.1, 4.1)).append("loc2", new Point(new Position(10.3, 10.3)));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4));
		
		mc.createIndex(geo2d("loc1"));
		mc.createIndex(geo2dsphere("loc2"));
		
		//geoWithin 正方形
		FindIterable<Document> iterable = mc.find(Filters.geoWithin("loc1", new Polygon(Arrays.asList(new Position(0, 0), new Position(4, 0), new Position(4, 4), new Position(0, 4), new Position(0, 0)))));
		printResult("Filters.geoWithin", iterable);
		
		//near
		iterable = mc.find(Filters.near("loc2", new Point(new Position(10, 10)), 16000d, 0d));
		printResult("Filters.near", iterable);
		
		//geoWithinCenter
		iterable = mc.find(Filters.geoWithinCenter("loc1", 0d, 0d, 3.5));
		printResult("Filters.geoWithinCenter", iterable);
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
