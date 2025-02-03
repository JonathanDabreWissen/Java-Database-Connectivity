
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;


public class MongoDBOperations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("*************MONGO DB OPERATIONS DEMO****************");
		
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		MongoDatabase database = mongoClient.getDatabase("demodb");
		
		MongoCollection<Document> collection = database.getCollection("Employee");
		
		
		
//		Document doc = new Document();
//		doc.append("name", "Pooja");
//		doc.append("age", 33);
//		doc.append("salary", 60000);
//		doc.append("designation", "Programmer");
		
//		doc.append("name", "Vivek");
//		doc.append("age", 43);
//		doc.append("salary", 75000);
//		doc.append("designation", "Manager");
		
//		doc.append("name", "Vivek");
//		doc.append("age", 43);
//		doc.append("salary", 75000);
//		doc.append("designation", "Manager");
//		
//		Document doc1 = new Document();
//		Document doc2 = new Document();
		
//		collection.insertOne(doc);
//		collection.insertOne(new Document().append("name", "Suman").append("age", 49).append("salary", 150000).append("designation", "Manager"));
		
		/*
		List<Document> employees = new ArrayList<Document>();
		
		
		employees.add(new Document().append("name", "Raju").append("age", 22).append("salary", 150000).append("designation", "Manager"));
		employees.add(new Document().append("name", "Manju").append("age", 29).append("salary", 150000).append("designation", "Clerk"));
		employees.add(new Document().append("name", "Sanju").append("age", 49).append("salary", 25000).append("designation", "Programmar"));
		employees.add(new Document().append("name", "Viju").append("age", 32).append("salary", 15000).append("designation", "Clerk"));
		employees.add(new Document().append("name", "Kiran").append("age", 35).append("salary", 27000).append("designation", "Clerk"));
		collection.insertMany(employees);
		*/
		/*
		Bson projection1 = Projections.excludeId();
//		Bson projection2 = Projections.exclude("age", "salary");
		Bson projection2 = Projections.exclude("id_", "age", "salary");
		Bson filter = Filters.eq("designation", "Programmer");
		Bson asort = Sorts.ascending("salary");
		*/
		
		
		
//		FindIterable<Document> i = collection.find().projection(projection2).projection(projection1);
//		FindIterable<Document> i = collection.find().projection(projection2);
//		FindIterable<Document> i = collection.find(filter).projection(projection1).sort(asort);
		
		/*
		
		for(Document d: i) {
			System.out.println(d.toJson());
		}
		*/
		
//		Bson filter = Filters.eq("designtation", "Programmer");
//		Bson filter1 = Filters.lte("age", 33);
//		Bson update = Updates.set("designation", "Programmer");
//		collection.updateMany(filter1, update);
		
		Bson filter2 = Filters.eq("name", "Suman");
		collection.deleteOne(filter2);
		
		
		System.out.println("Successfully connected to Mongo DB...");
		mongoClient.close();
	}

}
