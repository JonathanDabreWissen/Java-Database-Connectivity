import java.sql.*;
import org.json.JSONObject;


class JDBCConnection{
	
	 private static String jdbcURL = "jdbc:postgresql://localhost:5432/demodb";
     private static  String username = "postgres";
     private static String password = "tiger";
     
     private static Connection conn = null;
     
     private JDBCConnection() {
    	 try {
    		 conn= DriverManager.getConnection(jdbcURL, username, password);
    	 }
    	 catch (Exception e) {
    		 System.out.println(e);
    	 }
     }
     
     public static Connection getConnection() {
    	 if(conn == null) {
    		 new JDBCConnection();
    	 }
    	 
    	 return conn;
     }
     
}

class SalesRecord{
	
	public static void createRecord(String product, String month, int sales_amount, String category, int quantity, String location) {
		Connection conn = JDBCConnection.getConnection();
		
		PreparedStatement statement = null;
		try {
			String insertQuery = "INSERT INTO sales (product, month, sales_amount, sales_data) VALUES(?, ?, ?, ?::jsonb)";	
			statement = conn.prepareStatement(insertQuery);
			
			JSONObject json = new JSONObject();
			json.put("category", category); 
			json.put("quantity", quantity);
			json.put("location", location);
			
			statement.setString(1, product);
			statement.setString(2,  month);
			statement.setInt(3, sales_amount);
			statement.setString(4, json.toString());
			
			statement.executeUpdate();
			System.out.println("Inserted Successfully");
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void displayRecords() {
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		 
		 
		try {
			String selectQuery = "SELECT product, month, sales_amount, sales_data FROM sales";
            statement = conn.prepareStatement(selectQuery);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
            	String product = resultSet.getString("product");
                String month = resultSet.getString("month");
                int salesAmount = resultSet.getInt("sales_amount");
                String salesDataJson = resultSet.getString("sales_data");

                // Parse JSON data
                JSONObject salesData = new JSONObject(salesDataJson);
                System.out.println("Product: " + product);
                System.out.println("Month: " + month);
                System.out.println("Sales Amount: " + salesAmount);
                System.out.println("Sales Data: " + salesData.toString(2));
            }
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public static void updateJsonData(String product, String category, int quantity, String location) {
		
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement statement = null;
		
		
		try {
			
            String updateQuery = "UPDATE sales SET sales_data = ?::jsonb WHERE product = ?";
            statement = conn.prepareStatement(updateQuery);
            
            JSONObject updatedJson = new JSONObject();
            updatedJson.put("category", category); 
			updatedJson.put("quantity", quantity);
			updatedJson.put("location", location);
			
			statement.setString(1, updatedJson.toString()); // Set new JSON
	        statement.setString(2, product);
	        
	        
	        int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) updated successfully");
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public static void deleteRecord(String ProductName) {
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement statement = null;
		try {
			String deleteQuery = "DELETE FROM sales WHERE product = ?";
            statement = conn.prepareStatement(deleteQuery);
            statement.setString(1, ProductName);
            
            
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " record(s) deleted successfully");
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}

public class PostgreSQLOperations {

    public static void main(String[] args) {    	
    	
//    	SalesRecord.createRecord("iPhone 16", "March", 1000000, "Phone", 10, "Mumbai");
//    	SalesRecord.createRecord("S25 Ultra", "February", 1000000, "Phone", 10, "Mumbai");
//    	SalesRecord.createRecord("Pixel 9", "May", 1000000, "Phone", 10, "Mumbai");
//    	SalesRecord.deleteRecord("Pixel 9");
    	
//    	SalesRecord.displayRecords();
    	SalesRecord.displayRecords();
    	//SalesRecord.updateJsonData("S25 Ultra", "Smart Phone", 10, "Mumbai");
    	SalesRecord.displayRecords();
    	
        
    }
}
