package _mongodb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.LineString;
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
		
		Document doc1 = new Document("name", "tom").append("sex", "m").append("raid", Arrays.asList(10, 10)).append("gps", new Point(new Position(10, 10)));
		Document doc2 = new Document("name", "jone").append("sex", "f").append("raid", Arrays.asList(10.1, 10)).append("gps", new Point(new Position(10.1, 10)));
		Document doc3 = new Document("name", "john").append("sex", "m").append("raid", Arrays.asList(10, 10.1)).append("gps", new Point(new Position(10, 10.1)));
		Document doc4 = new Document("name", "jack").append("sex", "m").append("raid", Arrays.asList(9.9, 10)).append("gps", new Point(new Position(9.9, 10)));
		Document doc5 = new Document("name", "mary").append("sex", "f").append("raid", Arrays.asList(10, 9.9)).append("gps", new Point(new Position(10, 9.9)));
		Document doc6 = new Document("name", "abby").append("sex", "f").append("raid", Arrays.asList(10.2, 10)).append("gps", new Point(new Position(10.2, 10)));
		Document doc7 = new Document("name", "adam").append("sex", "m").append("raid", Arrays.asList(10.3, 10)).append("gps", new Point(new Position(10.3, 10)));
		Document doc8 = new Document("name", "barry").append("sex", "m").append("raid", Arrays.asList(10.4, 10)).append("gps", new Point(new Position(10.4, 10)));
		Document doc9 = new Document("name", "anne").append("sex", "f").append("raid", Arrays.asList(10.5, 10)).append("gps", new Point(new Position(10.5, 10)));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5, doc6, doc7, doc8, doc9));
		
		mc.createIndex(geo2d("raid"));
		mc.createIndex(geo2dsphere("gps"));
		
		//$geoWithin 在多边形内的文档
		FindIterable<Document> iterable = mc.find(Filters.geoWithin("raid", new Polygon(Arrays.asList(new Position(10.2, 10), new Position(10, 10.2), new Position(9.8, 10), new Position(10, 9.8), new Position(10.2, 10)))));
		printResult("Filters.geoWithin raid", iterable);
		
		//$geoWithinBox
		iterable = mc.find(Filters.geoWithinBox("raid", 9.8, 9.8, 10.2, 10.2));
		printResult("Filters.geoWithinBox raid", iterable);
		
		//$geoWithinPolygon
		List<Double> p1 = new ArrayList<>();
		List<Double> p2 = new ArrayList<>();
		List<Double> p3 = new ArrayList<>();
		p1.add(10d);
		p1.add(10d);
		p2.add(10.1);
		p2.add(10.16);
		p3.add(10.2);
		p3.add(10d);
		List<List<Double>> polygon = Arrays.asList(p1, p2, p3);
		iterable = mc.find(Filters.geoWithinPolygon("raid", polygon));
		printResult("Filters.geoWithinPolygon raid", iterable);

		p2.clear();
		p2.add(9.9);
		p2.add(10.16);
		p3.clear();
		p3.add(9.8);
		p3.add(10d);
		polygon = Arrays.asList(p1, p2, p3);
		iterable = mc.find(Filters.geoWithinPolygon("gps", polygon));
		printResult("Filters.geoWithinPolygon gps", iterable);
		
		//$geoWithinCenter
		iterable = mc.find(Filters.geoWithinCenter("raid", 10d, 10d, 0.25));
		printResult("Filters.geoWithinCenter raid", iterable);
		
		//$geoWithinCenterSphere 单位弧度(公里数/6371)
		iterable = mc.find(Filters.geoWithinCenterSphere("gps", 10d, 10d, 11d/6371));
		printResult("Filters.geoWithinCenterSphere gps", iterable);

		//$geoIntersects
		iterable = mc.find(Filters.geoIntersects("gps", new LineString(Arrays.asList(new Position(10, 10.1), new Position(10.1, 10), new Position(10, 9.9)))));
		printResult("Filters.geoIntersects gps", iterable);
		
		//$near 不支持2d
		iterable = mc.find(Filters.near("gps", new Point(new Position(10, 10)), 20566d, 0d));
		printResult("Filters.near gps", iterable);
		
		//$nearSphere 不支持2d
		iterable = mc.find(Filters.nearSphere("gps", new Point(new Position(10, 10)), 20566d, 10d));
		printResult("Filters.nearSphere gps", iterable);
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
