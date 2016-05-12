package _mongodb;

import java.text.ParseException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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

		// 插入用于测试的文档
		Document doc1 = new Document("title", "good day")
				.append("owner", "tom")
				.append("words", 300)
				.append("comments",
						Arrays.asList(
								new Document("author", "joe")
										.append("score", 3).append("comment",
												"good"),
								new Document("author", "white").append("score",
										1).append("comment", "oh no")));
		Document doc2 = new Document("title", "good")
				.append("owner", "john")
				.append("words", 400)
				.append("comments",
						Arrays.asList(
								new Document("author", "william").append(
										"score", 4).append("comment", "good"),
								new Document("author", "white").append("score",
										6).append("comment", "very good")));
		Document doc3 = new Document("title", "good night")
				.append("owner", "mike").append("words", 200)
				.append("tag", Arrays.asList(1, 2, 3, 4));
		Document doc4 = new Document("title", "happiness")
				.append("owner", "tom").append("words", 1480)
				.append("tag", Arrays.asList(2, 3, 4));
		Document doc5 = new Document("title", "a good thing")
				.append("owner", "tom").append("words", 180)
				.append("tag", Arrays.asList(1, 2, 3, 4, 5));
		mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));

		AggregateIterable<Document> iterable = mc.aggregate(Arrays.asList(
				match(in("owner", "tom", "john", "mike")),
				group("$owner", sum("totalWords", "$words"))));
		printResult(
				"like 'select sum(words) from blog where owner in(tom,john,mike) group by owner'",
				iterable);

		// $match 确定复合条件的文档, 可组合多个条件
		iterable = mc.aggregate(Arrays.asList(match(and(eq("owner", "tom"),
				gt("words", 300)))));
		printResult("$match only", iterable);

		// $sum求和 $avg平均值 $max最大值 $min最小值
		iterable = mc.aggregate(Arrays.asList(
				match(in("owner", "tom", "john", "mike")),
				group("$owner", sum("totalWords", "$words"),
						avg("averageWords", "$words"),
						max("maxWords", "$words"), min("minWords", "$words"))));
		printResult("$sum $avg $max $min", iterable);

		// $out 把聚合结果输出到集合
		mc.aggregate(Arrays.asList(
				match(in("owner", "tom", "john", "mike")),
				group("$owner", sum("totalWords", "$words"),
						avg("averageWords", "$words"),
						max("maxWords", "$words"), min("minWords", "$words")),
				out("wordsCount")));
		iterable = database.getCollection("wordsCount").aggregate(
				Arrays.asList(sample(3)));
		printResult("$out", iterable);

		// 随机取3个文档, 仅返回title和owner字段
		iterable = mc.aggregate(Arrays.asList(sample(3),
				project(fields(include("title", "owner"), excludeId()))));
		printResult("sample(3)", iterable);

		// 从第2个文档开始取2个文档, 仅返回title和owner字段
		iterable = mc.aggregate(Arrays.asList(skip(1), limit(2),
				project(fields(include("title", "owner"), excludeId()))));
		printResult("skip(1), limit(2)", iterable);

		// $lookup 和另一个集合关联
		database.getCollection("scores").drop();
		database.getCollection("scores").insertMany(
				Arrays.asList(
						new Document("writer", "tom").append("score", 100),
						new Document("writer", "joe").append("score", 95),
						new Document("writer", "john").append("score", 80)));
		iterable = mc.aggregate(Arrays.asList(lookup("scores", "owner",
				"writer", "joinedOutput")));
		printResult("lookup", iterable);

		// 拆分comments为单个文档
		iterable = mc.aggregate(Arrays.asList(match(size("comments", 2)),
				project(fields(include("comments"), excludeId())),
				unwind("$comments")));
		printResult("unwind comments", iterable);

		System.out.println("distinct");
		DistinctIterable<String> di = mc.distinct("owner", String.class);
		di.forEach(new Block<String>() {
			public void apply(final String str) {
				System.out.println(str);
			}
		});
		System.out.println("------------------------------------------------------");
		System.out.println();

		System.out.println("count");
		long count = mc.count(Filters.eq("owner", "tom"));
		System.out.println("count=" + count);
		System.out.println("------------------------------------------------------");
		System.out.println();

		System.out.println("mapreduce");
		String map = "function() { var category; "
				+ "if ( this.words >= 280 ) category = 'Long blogs'; "
				+ "else category = 'Short blogs'; "
				+ "emit(category, {title: this.title});}";

		String reduce = "function(key, values) { var cnt = 0; "
				+ "values.forEach(function(doc) { cnt += 1; }); "
				+ "return {count: cnt};} ";
		MapReduceIterable<Document> mi = mc.mapReduce(map, reduce);
		mi.forEach(new Block<Document>() {
			public void apply(final Document str) {
				System.out.println(str);
			}
		});
		System.out.println("------------------------------------------------------");
		System.out.println();
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
