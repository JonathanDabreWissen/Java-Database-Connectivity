import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class JdbcDemo4
{
    public static void main(String[] args) {
        
        try {
           
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            
            Statement stmt = con.createStatement();

            stmt.addBatch("insert into employee values('Aman', 25)");
            stmt.addBatch("insert into employee values('Babita', 35)");
            stmt.addBatch("insert into employee values('Chetan', 45)");
            stmt.addBatch("insert into employee values('Daniel', 23)");
            stmt.addBatch("insert into employee values('Hameed', 38)");

            System.out.println("Wait for 10 seconds to see the whole batch execution....");
            Thread.sleep(10000);

            stmt.executeBatch();
            System.out.println("You will see the records now");
            stmt.close();;
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%