import java.sql.*;


public class ResultSetDemo {
    public static void main(String[] args) {
        try {
            
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            // Statement stmt = con.createStatement();
            // Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            // Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs = stmt.executeQuery("select * from Employee");
            
            while (rs.next()) {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: " +rs.getInt(3));
                System.out.println();
                
            }

            System.out.println("--------------------------");
            while (rs.previous()) {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: "+ rs.getInt(3));
                System.out.println();
                
            }
            System.out.println("--------------------------");
            rs.absolute(3);
            {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: " +rs.getInt(3));
            }
            System.out.println("------------------------------");

            rs.relative(-2);
            {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: " +rs.getInt(3));
            }
            System.out.println("------------------------------");

            rs.last();
            {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: " +rs.getInt(3));
            }
            System.out.println("------------------------------");

            rs.first();
            {
                System.out.println("Name: " +rs.getString(2));
                System.out.println("Age: " +rs.getInt(3));
            }
            System.out.println("------------------------------");

            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
