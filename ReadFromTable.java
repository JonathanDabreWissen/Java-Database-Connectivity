import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ReadFromTable {
    public static void main(String[] args) {
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter table name: ");
            String table = br.readLine();
            
            //  Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("select * from " + table);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columns = rsmd.getColumnCount();

            
            while(rs.next()){
                for(int i = 1; i<= columns; i++){
                    System.out.println(rsmd.getColumnName(i) +": " +rs.getString(i));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
