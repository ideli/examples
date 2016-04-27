package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
		
		Document doc1 = new Document("name", "xiao ming").append("location", Arrays.asList(1, 100)).append("loc", new Document("type", "Point").append("coordinates", Arrays.asList(10, 10)));
		Document doc2 = new Document("name", "xiao hong").append("location", Arrays.asList(10, 30)).append("loc", new Document("type", "Point").append("coordinates", Arrays.asList(10, 10)));
		Document doc3 = new Document("name", "da pang").append("location", Arrays.asList(180, 5)).append("loc", new Document("type", "Point").append("coordinates", Arrays.asList(10, 10)));
		Document doc4 = new Document("name", "xiao hu").append("location", Arrays.asList(50, 50)).append("loc", new Document("type", "Point").append("coordinates", Arrays.asList(10, 10)));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4));
		
		mc.createIndex(geo2d("location"));
		mc.createIndex(geo2dsphere("loc"));
	}
}
