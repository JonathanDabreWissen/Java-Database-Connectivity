// package Assignments;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.sql.*;

interface EmpDAO {

   public void createRecord();
   public void displayAllRecords();
}

class EmployeeRecord{
    
    public static void createRecord(Employ employ ){

        try {            

        	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    		
    		MongoDatabase database = mongoClient.getDatabase("demodb");
    		
    		MongoCollection<Document> collection = database.getCollection("Employee");
    		
    		Document doc = new Document();
    		doc.append("eid", employ.eid);
    		doc.append("name", employ.name);
    		doc.append("age", employ.age);
    		doc.append("salary", employ.salary);
    		doc.append("desingation", employ.designation);
    		doc.append("department", employ.department);
    		
    		collection.insertOne(doc);
    		
    		mongoClient.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void displayAllRecords(){
        try {
        	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    		
    		MongoDatabase database = mongoClient.getDatabase("demodb");
    		
    		MongoCollection<Document> collection = database.getCollection("Employee");

            FindIterable<Document> i = collection.find();
            
            
            
            for(Document d: i) {
            	//System.out.println(d.toJson());
            	System.out.println("----------------------------");
            	
            	for(Map.Entry<String, Object> entry : d.entrySet()) {
            		System.out.println(entry.getKey() +": " +entry.getValue());
            	}
            	System.out.println("----------------------------");
            }
            
            mongoClient.close();
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean checkIfUserExists(int empId) {
    	
    	boolean result = false;
    	try {
    		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    		
    		MongoDatabase database = mongoClient.getDatabase("demodb");
    		
    		MongoCollection<Document> collection = database.getCollection("Employee");
    		
    		Bson filter = Filters.eq("eid", empId);
    		long count = 0;
    		
    		count = collection.countDocuments(filter);
    		
    		
    		
    		if(count>0) {
    			System.out.println("Employ already exists");
    			result = true;
    		}	
    		
    		
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	
    	
    	return result;
    }

    public static void displayRecord(int empId){
        try {
            

        	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    		
    		MongoDatabase database = mongoClient.getDatabase("demodb");
    		
    		MongoCollection<Document> collection = database.getCollection("Employee");
    		
    		Bson filter = Filters.eq("Eid", empId);
    		
    		Document document = collection.find(filter).first();

    		if(document != null) {
    			
    			System.out.println("----------------------------");
            	for(Map.Entry<String, Object> entry : document.entrySet()) {
            		System.out.println(entry.getKey() +": " +entry.getValue());
            	}
            	System.out.println("----------------------------");
    			
    		}
    		else {
    			System.out.println("Employ not found.");
    		}
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Loophole
    public static void updateSalary(int eid, int  newSalary){
        try {

            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setUrl("jdbc:postgresql://localhost:5432/demodb");
            rowSet.setUsername("postgres");
            rowSet.setPassword("tiger");

            rowSet.setCommand("SELECT eid, name, salary FROM employee WHERE eid = ? ");
            rowSet.setInt(1, eid);
            rowSet.execute();

            if(rowSet.next()){
                rowSet.updateInt("salary", newSalary);
                rowSet.updateRow();

                try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger")){
                    conn.setAutoCommit(false);
                    rowSet.acceptChanges(conn);
                }catch(Exception e){
                    System.out.println("Error while commiting: " +e);
                }
            }
            else{
                System.out.println("Employee not found");
            }
            rowSet.close();       
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteRecord(int eid){
        try {

            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setUrl("jdbc:postgresql://localhost:5432/demodb");
            rowSet.setUsername("postgres");
            rowSet.setPassword("tiger");

            rowSet.setCommand("SELECT eid, name, salary FROM employee WHERE eid = ? ");
            
            rowSet.setInt(1, eid);

            rowSet.execute();

            if(rowSet.next()){
                rowSet.deleteRow();
                System.out.println("Record marked for deletion...");


                try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger")){
                    conn.setAutoCommit(false);
                    rowSet.acceptChanges(conn);
                }catch(Exception e){
                    System.out.println("Error while commiting: " +e);
                }
            }
            else{
                System.out.println("Employ not found.");
            }     

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

abstract class Employ{
    int eid;
    String name;
    String designation;
    String department;
    int salary;
    int age;

    static int employCount = 1;

    public void display() {
        System.out.println("\nHere are the employ details");
        System.out.println("Employ ID: " + eid);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Designation: " + designation);
        System.out.println("Salary: " + salary);
        System.out.println();
    }

    public static boolean validAge(int age) {

        if (age < 21 || age > 60) {
            return false;
        }
        return true;
    }

    public static boolean validName(String name) {
        String nameRegex = "^[A-Z][a-z]* [A-Z][a-z]*$";

        Pattern namePattern = Pattern.compile(nameRegex);
        Matcher nameMatcher = namePattern.matcher(name);
        if (nameMatcher.find()) {
            return true;
        }

        return false;
    }

    Employ(int eid, String name, int age, String designation, int salary) {
        
        employCount++;
        this.eid = eid;
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.salary = salary;
        this.department = "IT";

        // EmployeeRecord.createRecord(name, age, salary, designation, "IT");
        System.out.println("\nCreated Employ Successfully");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Employ ID: " + eid);
        System.out.println("Designation: " + designation);
        System.out.println("Base Salary: " + salary);
        System.out.println();

    }

    abstract void raiseSalary(int eid);

}

final class Clerk extends Employ {

    Clerk(int eid, String name, int age) {
        super( eid, name, age, "Clerk", 25000);
    }

    Clerk(int eid, String name, int age, int salary){
        super(eid, name, age, "Clerk", salary);

    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.eid);

        this.salary = this.salary + 1000;
        EmployeeRecord.updateSalary(this.eid, this.salary);
        System.out.println("Salary now: " + this.salary);

    }
}

final class Programmer extends Employ {

    Programmer(int eid, String name, int age) {
        super(eid, name, age, "Programmer", 50000);
    }
    Programmer(int eid, String name, int age, int salary){
        super( eid, name, age, "Programmer", salary);
    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.eid);

        this.salary = this.salary + 2000;
        EmployeeRecord.updateSalary(this.eid, this.salary);
        System.out.println("Salary now: " + this.salary);

    }

}

final class Manager extends Employ {

    Manager(int eid, String name, int age) {
        super(eid, name, age, "Manager", 100000);
    }

    Manager(int eid, String name, int age, int salary){
        super(eid, name, age, "Manager", salary);
    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.eid);

        this.salary = this.salary + 10000;
        EmployeeRecord.updateSalary(this.eid, this.salary);
        System.out.println("Salary now: " + this.salary);

    }

}

public class EmployManagementWithMongo {


    public static void main(String[] args) {
        
        int choice=0;

        do {

            Scanner scanner = new Scanner(System.in);


            System.out.println("----------------------------------");
            System.out.println("\nMenu");
            System.out.println("1. Create Employ");
            System.out.println("2. Display Employs");
            System.out.println("3. Appraisal");
            System.out.println("4. Search Employ");
            System.out.println("5. Remove Employ");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format");
                scanner.nextLine();
                continue;
            }
            
            System.out.println("----------------------------------");


            switch (choice) {
                case 1: 
                        // create employ
                        System.out.println("1. Clerk \n2. Programmer \n3. Manager \n4. Others \n5. Exit");
                        System.out.print("Enter your choice of Employ: ");
                        int empChoice;
                        try {
                            empChoice = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input format");
                            scanner.nextLine();
                            continue;
                        }
                        
                        scanner.nextLine();
                        
                        int empId;
                        
                        do {
                        	System.out.print("Enter Id: ");
                        	empId = scanner.nextInt();
                        }
                        while(EmployeeRecord.checkIfUserExists(empId));
                        

                        scanner.nextLine(); 

                        String empName;
                        int age;

                        System.out.print("Enter name: ");
                        empName = scanner.nextLine();

                        System.out.print("Enter age: ");
                        age = scanner.nextInt();

                        Employ tempEmploy = null;

                        if (empChoice == 1) {
                            tempEmploy = new Clerk(empId, empName, age);
                        } else if (empChoice == 2) {
                            tempEmploy = new Programmer(empId, empName, age);
                        } else if (empChoice == 3) {
                            tempEmploy = new Manager(empId, empName, age);
                        } else if( empChoice == 4){
                            System.out.println("currently not available.");
                        }
                        else {
                            System.out.println("Check your choice.");
                            continue;
                        }

                        EmployeeRecord.createRecord(tempEmploy);

                        break;
                
                case 2:
                        //Display
                        EmployeeRecord.displayAllRecords();
                        break;
                    

                case 3:
                        //appraisal
                        System.out.print("Enter Id: ");
                        empId = scanner.nextInt();
                        EmployeeRecord.displayRecord(empId);

                        System.out.println("Above are the employee details");

                        System.out.print("Enter new salary: ");
                        int newSalary = scanner.nextInt();

                        EmployeeRecord.updateSalary(empId, newSalary);
                        System.out.println("Salary Updated Successfully!!");
                        EmployeeRecord.displayRecord(empId);

                        System.out.println("");
                        break;
                     
                    
                case 4:
                        //search
                        System.out.print("Enter Id: ");
                        empId = scanner.nextInt();
                        
                        EmployeeRecord.displayRecord(empId);
                        break;
                
                    
                
                case 5:
                        //Remove
                        System.out.print("Enter Id: ");
                        empId = scanner.nextInt();
                        EmployeeRecord.displayRecord(empId);

                        System.out.println("Above are the employee details, are you sure you want to delete? ");

                        System.out.print("Enter Yes/No: ");
                        scanner.nextLine();
                        String confirmation = scanner.nextLine();

                        if(confirmation.equalsIgnoreCase("Yes")){
                            EmployeeRecord.deleteRecord(empId);
                            System.out.println("Deleted Successfully!!");
                        }else{
                            continue;
                        }
                        break;

                  

                case 6: 
                        scanner.close();
                        System.out.println("Exiting the code");
                        break;
                    
                default:
                        System.out.println("Check your choice");
                    
            }
            
        } while (choice !=6);
    }
    
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%