import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class JdbcDemo2{
    public static void main(String[] args) {
        
        try {
           
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            PreparedStatement pstmt = con.prepareStatement("insert into employee values(?, ?)");

            
            System.out.print("Enter name: ");
            String name = br.readLine();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(br.readLine());

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.execute();



            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%