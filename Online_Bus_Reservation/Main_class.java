package Online_Bus_Reservation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Main_class {
	static Scanner sc = new Scanner(System.in);
	static Scanner scs = new Scanner(System.in);
	public static void main(String args[])
	{
		int Choise;
		Agent ag = new Agent();
		while(ag.Login)
		{
			System.out.println("Categories :  1:Booking 2:Search_ticket 3:Refund 4:Cancel_ticket 5:Exit :");
			Choise = sc.nextInt();
			switch(Choise)
			{
			case 1:   //Add details 
				Book_Ticket();
				break;
			case 2: //Search Ticket 
				System.out.print("Enter the Tiket NO : ");
				String  Ticket_No =  scs.nextLine();
				Search_Ticket(Ticket_No);
				break;
			case 3: //refund
				System.out.print("Enter the id Number :");
				String Id = scs.nextLine();
				Cutomer_Refund rs = new Cutomer_Refund();
				rs.RefundAmount(Id);
				break;
			case 4: //Cancel ticket
				System.out.print("Enter the Cust_id Number : ");
				String Cust_id = scs.nextLine();
				Cancel_ticket(Cust_id);
				break;
			case 5: // admin logout
				System.out.println("logout");
				ag.Login = false;
//				System.exit(0);			
			}
		}
		
		
	}
// -----------------function start--------------------------------
	static void Book_Ticket() 
	{
//		customer_details				
		Random r = new Random();
		String name,address,phone_no;
		int age;
		String id = "A"+r.nextInt(3000);
		System.out.println("Fill the Customer Details :");
		System.out.print("Name : ");
		name = scs.nextLine();
		System.out.print("age : ");
		age = sc.nextInt();
		System.out.print("Address  : ");
		address = scs.nextLine();
		System.out.print("phone_no : ");
		phone_no = scs.nextLine();
		Customer c = new Customer();
		String id_c;
		id_c=id;
		c.Add_details(id, name, address, phone_no, age);
		System.out.println("Add_details_Completed...");
		Ticket t = new Ticket();
		System.out.println("Ticket is Completed...");
		t.Travelto(id_c);
		t.printing(t);
	}
     static void Search_Ticket(String Ticket_no) 
	{
		try
		{
		Connection_Database db = new Connection_Database("online_bus_reservation");
		String Query = "select *from customer where Cust_id = ?";
		db.ps = db.con.prepareStatement(Query);
		db.ps.setString(1, Ticket_no); 
		db.rs = db.ps.executeQuery();	
		int len = 0;
		while(db.rs.next())
		{
			len = db.rs.getRow();
			System.out.println(" Id no : "+db.rs.getString(1)+ "\n Name : "+db.rs.getString(2)+ "\n Age :  "+db.rs.getString(3)
			+ "\n Address :  "+db.rs.getString(4)+ "\n Mobile no :  "+db.rs.getString(5));
		}
//		
		Query = "select *from Ticket where Cust_id = ?";
		db.ps = db.con.prepareStatement(Query);
		db.ps.setString(1, Ticket_no); 
		db.rs = db.ps.executeQuery();	
		while(db.rs.next())
		{
			len = db.rs.getRow();
			System.out.println(" From : "+db.rs.getString(2)+ "\n To : "+db.rs.getString(3)+ "\n Bus No :  "+db.rs.getString(4)
			+ "\n Seat no :  "+db.rs.getString(5)+ "\n Time : "+db.rs.getString(6)+ "\n Date :  "+db.rs.getString(7));
		}
		if(len != 0)
		{
			System.out.println("Ticket stored in database");
		}
		else
		{
			System.out.println("Unknown Ticket Number : "+Ticket_no+" ...please Check your ticket_No & Try Again...");
		}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void Cancel_ticket(String Ticket_no)
	{
		String Cust_id = "";
		int Amount = 0;
		try
		{
		Connection_Database db = new Connection_Database("online_bus_reservation");
		String Query = "select *from customer where Cust_id = ?";
		db.ps = db.con.prepareStatement(Query);
		db.ps.setString(1, Ticket_no); 
		db.rs = db.ps.executeQuery();	
		while(db.rs.next())
		{
				Query = "delete from customer where Cust_id = ?";
				db.ps = db.con.prepareStatement(Query);
				db.ps.setString(1, db.rs.getString(1));
				System.out.print("Are you confirm to Cancel this Ticket "+Ticket_no+" : (1.Yes/2.No)");
				int Key = sc.nextInt();
				if(Key == 1)
				{
					db.ps.executeUpdate();
					Query = "select *from ticket where Cust_id = ?";
					db.ps = db.con.prepareStatement(Query);
					db.ps.setString(1, Ticket_no);
					db.rs = db.ps.executeQuery();
					while(db.rs.next())
					{
						if(Ticket_no.equals(db.rs.getString(1)))
						{
							Cust_id = db.rs.getString(1);
							Amount = db.rs.getInt(8);
							Query  = "delete from ticket where Cust_id = ?";
							db.ps = db.con.prepareStatement(Query);
							db.ps.setString(1, Ticket_no);
							db.ps.executeUpdate();
						}
					}
					Query = "insert into refund values(?,?)";
					db.ps = db.con.prepareStatement(Query);
					db.ps.setString(1, Cust_id);
					db.ps.setInt(2, Amount);
					db.ps.executeUpdate();
				}
				else
				{
					System.out.println("Happy Journer : ... :)");
				}
		}			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

// -----------------function end--------------------------------
}
