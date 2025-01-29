import java.sql.*;
import java.util.Scanner;

public class JdbcDemo{
    public static void main(String[] args) {
        
        try {
           
            // Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            Statement stmt = con.createStatement();

            System.out.print("Enter name: ");
            String name = new Scanner(System.in).next();

            System.out.print("Enter age: ");
            int age = new Scanner(System.in).nextInt();

            //stmt.executeUpdate("insert into employee values('Raju', 25)");
            //stmt.executeUpdate("insert into employee values('Manju', 35)");
            // stmt.executeUpdate("update employee set age=age+1");
            //stmt.executeUpdate("delete from employee where Age<30");
            stmt.executeUpdate("insert into employee values('" +name +"', " +age +")");

            ResultSet rs = stmt.executeQuery("select * from employee");
            while(rs.next()){
                System.out.println("Name: " +rs.getString(1));
                System.out.println("Name: " +rs.getInt(2));
                System.out.println();
            }



            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%