import java.sql.*;

public class JdbcDemo{
    public static void main(String[] args) {
        
        try {
           
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into employee values('Raju', 25)");

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}