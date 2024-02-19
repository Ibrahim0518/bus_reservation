package Online_Bus_Reservation;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Ticket extends Customer {
	String Cust_id, from_, to_, busno, date, time;
	int seat_no, payment;

	public void Travelto(String Customer_id) {
		this.Cust_id = Customer_id;
		String from_city, to_city;
		int seat_num;
		Connection_Database db = new Connection_Database("online_bus_reservation");// database Name :
																					// online_bus_reservation
		Ticket ticket = new Ticket();
		
		System.out.println("Enter the destination : 1-Tirupattur 2-Vaniyambadi 3-Ambur 4-Madhanur 5-Palligonda");
		int choise = sc.nextInt();
		switch (choise) {
		case 1:
			this.from_ = "Tirupattur";
			break;
		case 2:
			this.from_ = "Vaniyambadi";
			break;
		case 3:
			this.from_ = "Ambur";
			break;
		case 4:
			this.from_ = "Madhanur";
			break;
		case 5:
			this.from_ = "Palligonda";
			break;
		}

		if (choise == 1) {
			System.out.println("Enter the destination : 1-Vaniyambadi 2-Ambur 3-Madhanur 4-Palligonda 5-Vellore");
			choise = sc.nextInt();
			switch (choise) {
			case 1:
				this.to_ = "Vaniyambadi";
				ticket.DateOfJurney(db, this.from_,this.to_, this.Cust_id);
				break;
			case 2:
				this.to_ = "Ambur";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 3:
				this.to_ = "Madhanur";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 4:
				this.to_ = "Palligonda";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 5:
				this.to_ = "Vellore";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			}
		} 
		else if (choise == 2) {
			System.out.println("Enter the destination : 1-Ambur 2-Madhanur 3-Palligonda 4-Vellore");
			choise = sc.nextInt();
			switch (choise) {
			case 1:
				this.to_ = "Ambur";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 2:
				this.to_ = "Madhanur";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 3:
				this.to_ = "Palligonda";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 5:
				this.to_ = "Vellore";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			}
		} else if (choise == 3) {
			System.out.println("Enter the destination : 1-Madhanur 2-Palligonda 3-Vellore");
			choise = sc.nextInt();
			switch (choise) {
			case 1:
				this.to_ = "Madhanur";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 2:
				this.to_ = "Palligonda";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 3:
				this.to_ = "Vellore";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			}

		} else if (choise == 4) {
			System.out.println("Enter the destination :1-Palligonda 2-Vellore");
			choise = sc.nextInt();
			switch (choise) {
			case 1:
				this.to_ = "Palligonda";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			case 2:
				this.to_ = "Vellore";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			}

		} else if (choise == 5) {
			System.out.println("Enter the destination : 1-Vellore");
			choise = sc.nextInt();
			switch (choise) {
			case 1:
				this.to_ = "Vellore";
				ticket.DateOfJurney(db, this.from_,this.to_,  this.Cust_id);
				break;
			}
		}

//		-------------------------------------

	}

//	are seat already booked or not?;
	public int seat_chicking(Connection_Database db) {
		Boolean b = true;
		int len = 0;
		String Query;
		int U_seat_no = 0;
		try {
			while (b) {
				System.out.println("Enter the seat_no : ");
				U_seat_no = sc.nextInt();
				this.seat_no = U_seat_no;
				Query = "select *from ticket where Seat_no = ?";
				db.ps = db.con.prepareStatement(Query);
				db.ps.setInt(1, this.seat_no);
				db.rs = db.ps.executeQuery();
				len = 0;
				while (db.rs.next()) {
					if (db.rs.getInt(5) == this.seat_no) {
						System.out.println("This seat number is booked...");
						len = 1;
					} else {
						len = 0;
					}
				}
				if (len == 0) {
					System.out.println("Seat is entry");
					b = false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return U_seat_no;
	}

	public void DateOfJurney(Connection_Database db, String from,String to, String id) {
		int len = 0;
		this.Cust_id = id;
		this.from_ = from;
		this.to_ = to;
		System.out.println("Enter Date  : this format -> (YYYY-MM-DD) eg:(2023-01-01) \n Enter the date:");
		String Date = scs.nextLine();
		this.date = Date;
		try {
			String Query = "select *from bus_info where _from = ? && _to = ?";
			db.ps = db.con.prepareStatement(Query);
			db.ps.setString(1, this.from_);
			db.ps.setString(2, this.to_);
			db.rs = db.ps.executeQuery();
			while (db.rs.next()) {
				this.busno = db.rs.getString(1);
				this.to_ = db.rs.getString(3);
				this.time = db.rs.getString(4);
				this.payment = db.rs.getInt(7);
			}
			db.rs = db.ps.executeQuery();
			System.out.println(this.busno);
			System.out.println(this.to_);
			System.out.println(this.time);
			System.out.println(this.payment);
			Query = "select *from ticket";
			db.rs = db.st.executeQuery(Query);
			System.out.println("runner");
			while (db.rs.next()) {
//					-----Checking to data and bus_no equal or not
				if (this.busno.equals(db.rs.getString(4)) && this.date.equals(db.rs.getString(7))) {
					len = 1;
					System.out.println(
							this.busno + " - " + db.rs.getString(4) + "| " + this.date + " - " + db.rs.getString(7));
					System.out.println(this.time + " " + db.rs.getString(6));
//						check to bus_time equal or not
					if (this.time.equals(db.rs.getString(6))) {
						System.out.println(" ---- time same-----");
						this.seat_no = seat_chicking(db);
						System.out.println("your seat is booking");
						Query = "insert into ticket (Cust_id,Sourse,Destination,Bus_no,Seat_no,time_ing,Date_of_Journey,Amount)values(?,?,?,?,?,?,?,?)";
						db.ps = db.con.prepareStatement(Query);
						db.ps.setString(1, this.Cust_id);
						db.ps.setString(2, this.from_);
						db.ps.setString(3, this.to_);
						db.ps.setString(4, this.busno);
						db.ps.setInt(5, this.seat_no);
						db.ps.setString(6, this.time);
						db.ps.setString(7, this.date);
						db.ps.setInt(8, this.payment);
						System.out.println("value is inserted successfully");
					} else {
						System.out.println(" ---- time diffrent-----");
						this.seat_no = seat_chicking(db);
						String Qry = "insert into ticket(Cust_id,Sourse,Destination,Bus_no,Seat_no,time_ing,Date_of_Journey,Amount)values(?,?,?,?,?,?,?,?)";
						db.ps = db.con.prepareStatement(Qry);
						db.ps.setString(1, this.Cust_id);
						db.ps.setString(2, this.from_);
						db.ps.setString(3, this.to_);
						db.ps.setString(4, this.busno);
						db.ps.setInt(5, this.seat_no);
						db.ps.setString(6, this.time);
						db.ps.setString(7, this.date);
						db.ps.setInt(8, this.payment);
					}
				}
			}
//				first data store into database so len = 0
			if (len == 0) {
				this.seat_no = seat_chicking(db);
				String Qry = "insert into ticket(Cust_id,Sourse,Destination,Bus_no,Seat_no,time_ing,Date_of_Journey,Amount)values(?,?,?,?,?,?,?,?)";
				db.ps = db.con.prepareStatement(Qry);
				db.ps.setString(1, this.Cust_id);
				db.ps.setString(2, this.from_);
				db.ps.setString(3, this.to_);
				db.ps.setString(4, this.busno);
				db.ps.setInt(5, this.seat_no);
				db.ps.setString(6, this.time);
				db.ps.setString(7, this.date);
				db.ps.setInt(8, this.payment);
			}
			db.ps.executeUpdate();
			System.out.println("Execute successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    void printing(Ticket id) 
	{
    	String Query = null;
    	try
    	{
    		Connection_Database db = new Connection_Database("online_bus_reservation");
    		System.out.println("printing process....");
    		FileWriter fr = new FileWriter(id.Cust_id+"print.doc");
    		Query = "select *from ticket where Cust_id = ?";
    		db.ps = db.con.prepareStatement(Query);
    		db.ps.setString(1, id.Cust_id);
    		db.rs = db.ps.executeQuery();
    		while(db.rs.next())
    		{
    			fr.write("\n======================================================================");
    			fr.write("\nCustomer ID        : "+db.rs.getString(1) + "Date Of Journey    : "+db.rs.getString(7));
    			fr.write("\n======================================================================");
    			fr.write("\nFrom               : "+db.rs.getString(2));
    			fr.write("\nTo                 : "+db.rs.getString(3));
    			fr.write("\nBsu No             : "+db.rs.getString(4));
    			fr.write("\nSeat No            : "+db.rs.getString(5));
    			fr.write("\nAmount             : "+db.rs.getString(8));
    			fr.write("\n======================================================================");    			
    		}
    		fr.close();
    		System.out.println("printing successfully....");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
	}
}
