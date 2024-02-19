package Online_Bus_Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection_Database {
	Connection con;
	Statement st;
	ResultSet rs;
	PreparedStatement ps;
	Connection_Database(String database_name)
	{
		String db_con = "jdbc:mysql://localhost:3307/"+database_name;
		try
		{
			this.con = DriverManager.getConnection(db_con, "root", "");
			this.st = con.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
