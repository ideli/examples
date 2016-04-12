package _mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

public class Client {

	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("test");
		
		Client client = new Client(database);
		client.insert(database);
		client.createSingleFieldIndex();
		client.queryAll();
		mongoClient.close();
	}
	
	private MongoDatabase database;
	public Client(MongoDatabase database) {
		this.database = database;
	}

	public void insert(MongoDatabase database) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
				Locale.ENGLISH);
		Document doc = new Document("address", new Document()
				.append("street", "2 Avenue").append("zipcode", "10075")
				.append("building", "1480")
				.append("coord", Arrays.asList(-73.9557413, 40.7720266)))
				.append("borough", "Manhattan")
				.append("cuisine", "Italian")
				.append("grades",
						Arrays.asList(
								new Document()
										.append("date",
												format.parse("2014-10-01T00:00:00Z"))
										.append("grade", "A")
										.append("score", 11),
								new Document()
										.append("date",
												format.parse("2014-01-16T00:00:00Z"))
										.append("grade", "B")
										.append("score", 17)))
				.append("name", "Vella").append("restaurant_id", "41704620");
		System.out.println(doc.toJson());
		database.getCollection("restaurants").insertOne(doc);
	}
	
	public void createSingleFieldIndex() {
		database.getCollection("restaurants").createIndex(new Document("cuisine", 1));
    }
	
	public void createCompoundIndex() {
		database.getCollection("restaurants").createIndex(new Document("cuisine", 1).append("address.zipcode", -1));
    }

	public void queryAll() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find();
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
    }
	
	public void logicalAnd() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("cuisine", "Italian").append("address.zipcode", "10075"));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.and(Filters.eq("cuisine", "Italian"), Filters.eq("address.zipcode", "10075")));
    }
	

    public void logicalOr() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("$or", Arrays.asList(new Document("cuisine", "Italian"),
                        new Document("address.zipcode", "10075"))));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.or(Filters.eq("cuisine", "Italian"), Filters.eq("address.zipcode", "10075")));
    }
    
    public void queryTopLevelField() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("borough", "Manhattan"));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.eq("borough", "Manhattan"));
    }
    
    public void queryEmbeddedDocument() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("address.zipcode", "10075"));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.eq("address.zipcode", "10075"));
    }
    
    public void queryFieldInArray() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("grades.grade", "B"));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.eq("grades.grade", "B"));
    }
    
    public void greaterThan() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("grades.score", new Document("$gt", 30)));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.gt("grades.score", 30));
    }
    
    public void lessThan() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find(
                new Document("grades.score", new Document("$lt", 10)));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find(Filters.lt("grades.score", 10));
    }
    
    public void sort() {
        FindIterable<Document> iterable = database.getCollection("restaurants").find()
                .sort(new Document("borough", 1).append("address.zipcode", 1));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        database.getCollection("restaurants").find().sort(Sorts.ascending("borough", "address.zipcode"));
    }
    
    public void removeMatchingDocuments() {
    	database.getCollection("restaurants").deleteMany(new Document("borough", "Manhattan"));
    }

    public void removeAllDocuments() {
    	database.getCollection("restaurants").deleteMany(new Document());
    }

    public void dropCollection() {
    	database.getCollection("restaurants").drop();
    }

    public void updateTopLevelFields() {
    	database.getCollection("restaurants").updateOne(new Document("name", "Juni"),
                new Document("$set", new Document("cuisine", "American (New)"))
                    .append("$currentDate", new Document("lastModified", true)));
    }

    public void updateEmbeddedField() {
    	database.getCollection("restaurants").updateOne(new Document("restaurant_id", "41156888"),
                new Document("$set", new Document("address.street", "East 31st Street")));
    }

    public void updateMultipleDocuments() {
    	database.getCollection("restaurants").updateMany(new Document("address.zipcode", "10016").append("cuisine", "Other"),
                new Document("$set", new Document("cuisine", "Category To Be Determined"))
                        .append("$currentDate", new Document("lastModified", true)));
    }

    public void replaceDocument() {
    	database.getCollection("restaurants").replaceOne(new Document("restaurant_id", "41704620"),
                new Document("address",
                        new Document()
                                .append("street", "2 Avenue")
                                .append("zipcode", "10075")
                                .append("building", "1480")
                                .append("coord", Arrays.asList(-73.9557413, 40.7720266)))
                        .append("name", "Vella 2"));
    }

}
