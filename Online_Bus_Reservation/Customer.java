package Online_Bus_Reservation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
	String Customer_id,Name,Address,Phone_Number;
	int Age;
	static Scanner sc = new Scanner(System.in);
	static Scanner scs = new Scanner(System.in);
	
	public void Add_details(String Cus_id,String name,String add,String P_no,int age)
	{
		this.Customer_id = Cus_id;
		this.Name = name;
		this.Address = add;
		this.Phone_Number = P_no;
		this.Age = age;		
		try
		{
			Connection_Database db = new Connection_Database("online_bus_reservation");
			String Query = "insert into customer values(?,?,?,?,?)";
			System.out.println("if you want to change the details (any Currections) :1 - yes /  2 - no");
			int key = sc.nextInt();
			if(key == 1)
			{
				modify();
				db.ps = db.con.prepareStatement(Query);
				db.ps.setString(1, this.Customer_id);
				db.ps.setString(2, this.Name);
				db.ps.setInt(3, this.Age);
				db.ps.setString(4, this.Address);
				db.ps.setString(5, this.Phone_Number);
				db.ps.executeUpdate();
				System.out.println("Successfully Inserted...:)");
			}
			else if(key == 2)
			{
				db.ps = db.con.prepareStatement(Query);
				db.ps.setString(1, this.Customer_id);
				db.ps.setString(2, this.Name);
				db.ps.setInt(3, this.Age);
				db.ps.setString(4, this.Address);
				db.ps.setString(5, this.Phone_Number);
				db.ps.executeUpdate();
				System.out.println();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
//	Customer Details Currection
	public void modify() throws SQLException
	{
		String change;
		boolean b = true;
		while(b != false)
		{
			int key ; 
			System.out.println("Modify 1 - Name 2 - Address 3 - Phone Number 4 - Age 5-exit");
			key = sc.nextInt();
			switch(key)
			{
			case 1 : 
				System.out.println("Enter the name : ");
				this.Name = scs.nextLine();
				break;
			case 2 :
				System.out.println("Enter the Address : ");
				this.Address = scs.nextLine();
				break;
			case 3 :
				System.out.println("Enter the Phone Number : ");
				this.Phone_Number = scs.nextLine();
				break;
			case 4:
				System.out.println("Enter the age : ");
				this.Age = sc.nextInt();
				break;
			case 5:
				b = false;
				break;
			}
		}
	}
}
