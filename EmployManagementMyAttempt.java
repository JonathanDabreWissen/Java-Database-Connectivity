// package Assignments;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

interface EmpDAO {

   public void createRecord();
   public void displayAllRecords();
}

class RowSetConnection{
    private static CachedRowSet rowSet = null;

    private RowSetConnection() {
        try {
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setUrl("jdbc:postgresql://localhost:5432/demodb");
            rowSet.setUsername("postgres");
            rowSet.setPassword("tiger");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CachedRowSet getRowSetConnection(){

        if(rowSet == null){
            new RowSetConnection();
        }
        
        return rowSet;
    }
}
class EmployeeRecord{
    
    public static void createRecord(Employ employ ){

        // String name, int age, int salary, String designation, String department 
        try {            

            CachedRowSet rowSet = RowSetConnection.getRowSetConnection();

            rowSet.setCommand("select name, age, salary, designation, department from employee");
            rowSet.execute();

            rowSet.moveToInsertRow();

            rowSet.updateString("name", employ.name);
            rowSet.updateInt("age", employ.age); 
            rowSet.updateInt("salary", employ.salary); 
            rowSet.updateString("designation", employ.designation);
            rowSet.updateString("department", employ.department);

            rowSet.insertRow();

            rowSet.moveToCurrentRow();

            try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger")){
                conn.setAutoCommit(false);
                rowSet.acceptChanges(conn);
            }catch(Exception e){
                System.out.println("Error while commiting: " +e);
            }


            rowSet.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void displayAllRecords(){
        try {
            CachedRowSet rowSet = RowSetConnection.getRowSetConnection();
            
            rowSet.setCommand("select * from employee");
            rowSet.execute();

            
            while(rowSet.next()){

                System.out.println("EmpID: " +rowSet.getInt(1));
                System.out.println("Name: " +rowSet.getString(2));
                System.out.println("Age: " +rowSet.getInt(3));
                System.out.println("Salary: " +rowSet.getInt(4));
                System.out.println("Designation: " +rowSet.getString(5));
                System.out.println("Department: " +rowSet.getString(6));
                System.out.println();
            }

            rowSet.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void displayRecord(int empId){
        try {
            
            CachedRowSet rowSet = RowSetConnection.getRowSetConnection();

            rowSet.setCommand("select * from employee where eid = ?");

            // Set parameter
            rowSet.setInt(1, empId);
            rowSet.execute();

    
            if(rowSet.next()){
                System.out.println("EmpID: " +rowSet.getInt(1));
                System.out.println("Name: " +rowSet.getString(2));
                System.out.println("Age: " +rowSet.getInt(3));
                System.out.println("Salary: " +rowSet.getInt(4));
                System.out.println("Designation: " +rowSet.getString(5));
                System.out.println("Department: " +rowSet.getString(6));
                System.out.println();
            }

            rowSet.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Loophole
    public static void updateSalary(int eid, int  newSalary){
        try {

            CachedRowSet rowSet = RowSetConnection.getRowSetConnection();

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

            CachedRowSet rowSet = RowSetConnection.getRowSetConnection();

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
    int empId = 0;
    String name;
    String designation;
    String department;
    int salary;
    int age;

    static int employCount = 1;

    public void display() {
        System.out.println("\nHere are the employ details");
        System.out.println("Employ ID: " + empId);
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

    Employ(String name, int age, String designation, int salary) {
        
        employCount++;
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.salary = salary;
        this.department = "IT";

        // EmployeeRecord.createRecord(name, age, salary, designation, "IT");
        System.out.println("\nCreated Employ Successfully");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Employ ID: " + empId);
        System.out.println("Designation: " + designation);
        System.out.println("Base Salary: " + salary);
        System.out.println();

    }

    abstract void raiseSalary(int empId);

}

final class Clerk extends Employ {

    Clerk(String name, int age) {
        super( name, age, "Clerk", 25000);
    }

    Clerk(String name, int age, int salary){
        super(name, age, "Clerk", salary);

    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.empId);

        this.salary = this.salary + 1000;
        EmployeeRecord.updateSalary(this.empId, this.salary);
        System.out.println("Salary now: " + this.salary);

    }
}

final class Programmer extends Employ {

    Programmer(String name, int age) {
        super(name, age, "Programmer", 50000);
    }
    Programmer( String name, int age, int salary){
        super( name, age, "Programmer", salary);
    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.empId);

        this.salary = this.salary + 2000;
        EmployeeRecord.updateSalary(this.empId, this.salary);
        System.out.println("Salary now: " + this.salary);

    }

}

final class Manager extends Employ {

    Manager(String name, int age) {
        super(name, age, "Manager", 100000);
    }

    Manager(String name, int age, int salary){
        super(name, age, "Manager", salary);
    }

    void raiseSalary(int empId) {
        System.out.println("\nRaised salary of " + this.name + " ,Employ ID: " + this.empId);

        this.salary = this.salary + 10000;
        EmployeeRecord.updateSalary(this.empId, this.salary);
        System.out.println("Salary now: " + this.salary);

    }

}

public class EmployManagementMyAttempt {


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
                case 1-> {
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

                        String empName;
                        int age;

                        System.out.print("Enter name: ");
                        empName = scanner.nextLine();

                        System.out.print("Enter age: ");
                        age = scanner.nextInt();

                        Employ tempEmploy = null;

                        if (empChoice == 1) {
                            tempEmploy = new Clerk(empName, age);
                        } else if (empChoice == 2) {
                            tempEmploy = new Programmer(empName, age);
                        } else if (empChoice == 3) {
                            tempEmploy = new Manager(empName, age);
                        } else if( empChoice == 4){
                            System.out.println("currently not available.");
                        }
                        else {
                            System.out.println("Check your choice.");
                            continue;
                        }

                        EmployeeRecord.createRecord(tempEmploy);



                    }
                
                case 2-> {
                        //Display
                        EmployeeRecord.displayAllRecords();
                    }

                case 3->{
                        //appraisal
                        System.out.print("Enter Id: ");
                        int empId = scanner.nextInt();
                        EmployeeRecord.displayRecord(empId);

                        System.out.println("Above are the employee details");

                        System.out.print("Enter new salary: ");
                        int newSalary = scanner.nextInt();

                        EmployeeRecord.updateSalary(empId, newSalary);
                        System.out.println("Salary Updated Successfully!!");
                        EmployeeRecord.displayRecord(empId);

                        System.out.println("");
                    }   
                    
                case 4 ->{
                        //search
                        System.out.print("Enter Id: ");
                        int empId = scanner.nextInt();
                        
                        EmployeeRecord.displayRecord(empId);
                    }
                
                case 5->{
                        //Remove
                        System.out.print("Enter Id: ");
                        int empId = scanner.nextInt();
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

                    }

                case 6->{
                        scanner.close();
                        System.out.println("Exiting the code");
                    }
                default ->{
                        System.out.println("Check your choice");
                    }
                    
            }
            
        } while (choice !=6);
    }
    
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%