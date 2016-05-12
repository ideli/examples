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
		
		Document doc1 = new Document("name", "tom").append("sex", "m").append("home", Arrays.asList(0, 0)).append("current", new Point(new Position(0, 0)));
		Document doc2 = new Document("name", "jone").append("sex", "f").append("home", Arrays.asList(1, 1)).append("current", new Point(new Position(1, 1)));
		Document doc3 = new Document("name", "john").append("sex", "m").append("home", Arrays.asList(-1, 1)).append("current", new Point(new Position(-1, 1)));
		Document doc4 = new Document("name", "jack").append("sex", "m").append("home", Arrays.asList(-1, -1)).append("current", new Point(new Position(-1, -1)));
		Document doc5 = new Document("name", "mary").append("sex", "f").append("home", Arrays.asList(1, -1)).append("current", new Point(new Position(1, -1)));
		Document doc6 = new Document("name", "abby").append("sex", "f").append("home", Arrays.asList(0.1, 0)).append("current", new Point(new Position(0.1, 0)));
		Document doc7 = new Document("name", "adam").append("sex", "m").append("home", Arrays.asList(0, 0.1)).append("current", new Point(new Position(0, 0.1)));
		Document doc8 = new Document("name", "barry").append("sex", "m").append("home", Arrays.asList(-0.1, 0)).append("current", new Point(new Position(-0.1, 0)));
		Document doc9 = new Document("name", "anne").append("sex", "f").append("home", Arrays.asList(0, -0.1)).append("current", new Point(new Position(0, -0.1)));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5, doc6, doc7, doc8, doc9));
		
		mc.createIndex(geo2d("home"));
		mc.createIndex(geo2dsphere("current"));
		//mc.createIndex(geoHaystack("home", ascending("sex")));
		
		//$geoWithin 在多边形内的文档
		FindIterable<Document> iterable = mc.find(Filters.geoWithin("home", new Polygon(Arrays.asList(new Position(-0.5, -0.5), new Position(0.5, -0.5), new Position(0.5, 0.5), new Position(-0.5, 0.5), new Position(-0.5, -0.5)))));
		printResult("Filters.geoWithin home", iterable);
		
		iterable = mc.find(Filters.geoWithin("current", new Polygon(Arrays.asList(new Position(-0.5, -0.5), new Position(0.5, -0.5), new Position(0.5, 0.5), new Position(-0.5, 0.5), new Position(-0.5, -0.5)))));
		printResult("Filters.geoWithin current", iterable);
		
		//$geoWithinBox
		iterable = mc.find(Filters.geoWithinBox("home", -0.5, -0.5, 0.5, 0.5));
		printResult("Filters.geoWithinBox home", iterable);
		
		iterable = mc.find(Filters.geoWithinBox("current", -0.5, -0.5, 0.5, 0.5));
		printResult("Filters.geoWithinBox current", iterable);
		
		//$geoWithinPolygon
		List<Double> p1 = new ArrayList<>();
		List<Double> p2 = new ArrayList<>();
		List<Double> p3 = new ArrayList<>();
		p1.add(0d);
		p1.add(0d);
		p2.add(0.1);
		p2.add(0.16);
		p3.add(0.2);
		p3.add(0d);
		List<List<Double>> polygon = Arrays.asList(p1, p2, p3);
		iterable = mc.find(Filters.geoWithinPolygon("home", polygon));
		printResult("Filters.geoWithinPolygon home", iterable);
		
		p2.clear();
		p2.add(-0.1);
		p2.add(0.16);
		p3.clear();
		p3.add(-0.2);
		p3.add(0d);
		polygon = Arrays.asList(p1, p2, p3);
		iterable = mc.find(Filters.geoWithinPolygon("current", polygon));
		printResult("Filters.geoWithinPolygon current", iterable);
		
		//$geoWithinCenter
		iterable = mc.find(Filters.geoWithinCenter("home", 0d, 0d, 0.2));
		printResult("Filters.geoWithinCenter home", iterable);
		
		iterable = mc.find(Filters.geoWithinCenter("current", 0d, 0d, 0.3));
		printResult("Filters.geoWithinCenter current", iterable);
		
		//$geoWithinCenterSphere 单位弧度(公里数/6371)
		iterable = mc.find(Filters.geoWithinCenterSphere("current", 0d, 0d, 20/6371));
		printResult("Filters.geoWithinCenterSphere current", iterable);
		
		//$geoIntersects
		iterable = mc.find(Filters.geoIntersects("current", new LineString(Arrays.asList(new Position(0, 0.1), new Position(0.1, 0), new Position(0, -0.1)))));
		printResult("Filters.geoIntersects current", iterable);
		
		//$near 不支持2d
		iterable = mc.find(Filters.near("current", new Point(new Position(0, 0)), 20566d, 0d));
		printResult("Filters.near current", iterable);
		
		//$nearSphere 不支持2d
		iterable = mc.find(Filters.nearSphere("current", new Point(new Position(0, 0)), 20566d, 0d));
		printResult("Filters.nearSphere current", iterable);
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
