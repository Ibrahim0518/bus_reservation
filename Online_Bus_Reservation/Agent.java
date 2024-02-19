package Online_Bus_Reservation;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Agent {
	int Agent_id;
	String Agent_name;
	String Agent_password;
	Boolean Login = false;
	static Scanner sc = new Scanner(System.in);
	static Scanner scs = new Scanner(System.in);
	
	static String PasswordHash(String pwd)
	{
		String password_hash = null;
		try
		{
			MessageDigest mg = MessageDigest.getInstance("SHA");//Secure Hashing Algoritham
			String password = pwd;
			mg.update(password.getBytes());
			
			byte[] resultsetArray = mg.digest();
			
			StringBuilder sb = new StringBuilder();
			
			for(byte b : resultsetArray)
			{
				sb.append(String.format("%02x", b));
			}
			password_hash = sb.toString();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return password_hash;
	}
	
	Agent()
	{
		String query;
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Online_Bus_Reservation", "root", "");
			Statement st = con.createStatement();
			ResultSet rs;
			PreparedStatement ps;
			int key;
			while(this.Login != true)
			{
				System.out.print("Enter "
						+ " the key : (1 = login  2 - sign up  3-exit) :  ");				
				key = sc.nextInt();
				System.out.println((int)key);
				switch(key)
				{
				case 1:
//					Login Admin program
					System.out.print("UserName : ");
					this.Agent_name = scs.nextLine();
					System.out.print("Password : ");
					this.Agent_password = scs.nextLine();
					String Query = "select *from agent where agent_name = ?";
					ps  = con.prepareStatement(Query);
					ps.setString(1, this.Agent_name);
					rs = ps.executeQuery();
					if(!rs.next())
					{
						System.out.println("Unknown username");
						System.out.println("please sign up  click 2: ");
					}
					else
					{
						String d_password = this.Agent_password;
						this.Agent_password = PasswordHash(d_password) ;
						System.out.println("user_Name : "+this.Agent_name);
						Query = "select *from agent where agent_password = ?";
						ps  = con.prepareStatement(Query);
						ps.setString(1, this.Agent_password);
						rs = ps.executeQuery();
						if(!rs.next())
						{
							System.out.println("password not matched");
							this.Login = false;
						}
						else
						{
							System.out.println("password matched");
							this.Login = true;
						}
					}
					break;
				case 2:
//					new Admin program
					String password,confirm_password;
					System.out.print("Enter the Name : ");
					this.Agent_name = scs.nextLine();
					System.out.print("Enter the password : ");
					password = scs.nextLine();
					System.out.print("Enter the confirm Password : ");
					confirm_password = scs.nextLine();
					if(password.equals(confirm_password))
					{
						this.Agent_password = password;
						query = "select *from agent where agent_name = ?";
						ps = con.prepareStatement(query);
						ps.setString(1, this.Agent_name);
						rs = ps.executeQuery();
						if(!rs.next())
						{
							this.Agent_password = PasswordHash(password);
							Query = "insert into agent (agent_name,agent_password) values(?,?)";
							ps = con.prepareStatement(Query);
							ps.setString(1, this.Agent_name);
							ps.setString(2, this.Agent_password);
							ps.executeUpdate();
							System.out.println("inserted Successfully");
						}
						else
						{
							System.out.println("alreay login");
						}
					}
					else
					{
						System.out.println("not same");
					}
				break;
				case 3:
					System.out.println("Logout");
					System.exit(0);
					break;
			
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
