import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;
public class RowSetDemo {
 
    public static void main(String args[]) {
        try {
            // RowSetFactory rsf = RowSetProvider.newFactory();
            // JdbcRowSet rs = rsf.createJdbcRowSet();
 
            JdbcRowSet rs = RowSetProvider.newFactory().createJdbcRowSet();
 
            rs.setUrl("jdbc:postgresql://localhost:5432/postgres");
            rs.setUsername("postgres");
            rs.setPassword("tiger");
 
            rs.setCommand("select * from EMPLOYEE");
            rs.execute();
 
            rs.addRowSetListener(new MyListener());
            rs.addRowSetListener(new MyListener2());
           
            rs.setCommand("select * from employee");
            rs.execute();
 
            while (rs.next()) {
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Age: " + rs.getInt(3));
                System.out.println();
            }
 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
class MyListener implements RowSetListener{
    public void cursorMoved(RowSetEvent e) {
        System.out.println("---------Cursor moved-------");
    }
    public void rowChanged(RowSetEvent e) {
        System.out.println("---------Row changed-------");
    }
 
    public void rowSetChanged(RowSetEvent e) {
        System.out.println("---------Row set changed-------");
    }
}
class MyListener2 implements RowSetListener{
    public void cursorMoved(RowSetEvent e) {
        System.out.println("-----------Cursor moved----------");
    }
    public void rowChanged(RowSetEvent e) {
        System.out.println("-----------Row changed----------");
    }
 
    public void rowSetChanged(RowSetEvent e) {
        System.out.println("-----------Row set changed----------");
    }
}