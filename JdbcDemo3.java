import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class JdbcDemo3{
    public static void main(String[] args) {
        
        try {
           
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            
            CallableStatement cstmt = con.prepareCall("Call dummy_record()");
            cstmt.execute();

            
            cstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

//set classpath=pgs_driver\postgresql-42.7.4.jar;.;%classpath%