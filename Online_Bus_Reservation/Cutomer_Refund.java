package Online_Bus_Reservation;
import java.sql.SQLException;

public class Cutomer_Refund extends Customer
{
	float Amount;
	Customer Customer_id;
	
	public void RefundAmount(String Customer_id)
	{
		try
		{
			Connection_Database db = new Connection_Database("Online_Bus_Reservation");
			String Query = "select *from refund where Cust_id = ? ";
			db.ps = db.con.prepareStatement(Query);
			db.ps.setString(1, Customer_id);
			db.rs = db.ps.executeQuery();
			while(db.rs.next())
			{
				System.out.println(db.rs.getString(1)+ " - Amount : "+db.rs.getInt(2));
			}
 		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
