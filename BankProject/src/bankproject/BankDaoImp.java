package bankproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//import com.mysql.jdbc.PreparedStatement;

public class BankDaoImp implements Bank
{

	String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
	
	//because every time we are using the Connection interface in every incomplete Method 
	//so put Connection as Non-primitive type 
	private Connection connection;
	
	@Override
	public void credit()
	{
		//not return type method so we simply call this method in the main method 
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your mobile number ");
		String mb = scan.next();
		System.out.println("Enter your password ");
		String pin = scan.next();
		
		String select = "select * from bank where mobilenumber=? and pin=?";
		
		try
		{
		connection = DriverManager.getConnection(url);
		PreparedStatement ps = connection.prepareStatement(select);//this statement is used  set the values to the data base
		ps.setString(1, mb);
		ps.setString(2, pin);
		
		ResultSet res = ps.executeQuery();
		if (res.next())
		{
			System.out.println("Enter the Amount ");
			double uamount = scan.nextDouble();
			if (uamount>0) 
			{
				double damount = res.getDouble(3);
				double add = damount+uamount;
				String update = "update bank set amount =? where mobilenumber=?";
				PreparedStatement ps1 =connection.prepareStatement(update);
				ps1.setDouble(1, add);
				ps1.setString(2, mb);
				
				int num = ps1.executeUpdate();
				if (num!=0)
				{
					System.out.println(uamount+" is Amount credited to your account available balance is : "+add);
				}
				else
				{
					System.out.println(" 404 error --means network error");
				}
				
			}
			else
			{
				System.out.println(" Enter the Valid Amount ");
			}
		}
		
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void debit() 
	{
		Scanner scan= new Scanner(System.in);
		System.out.println("Enter your mobile number ");
		String mb = scan.next();
		System.out.println("Enter your password ");
		String pin = scan.next();
		
		String select = "select * from bank where mobilenumber=? and pin=?";
		
		try 
		{
			connection = DriverManager.getConnection(url);
			PreparedStatement ps2 = connection.prepareStatement(select);
			ps2.setString(1, mb);
			ps2.setString(2, pin);
			
			ResultSet res = ps2.executeQuery();
			if (res.next())
			{
				System.out.println("Enter withdraw Amount ");
				double uamount = scan.nextDouble();
				if (uamount>0) 
				{
					double damount = res.getDouble(3);
					double sub=0;
					if (damount>=uamount)
					{
						sub =damount-uamount; 	
					
					String update = "update bank set amount =? where mobilenumber=?";
					PreparedStatement ps3 =connection.prepareStatement(update);
					ps3.setDouble(1, sub);
					ps3.setString(2, mb);
					
					int num = ps3.executeUpdate();
					if (num!=0)
					{
						System.out.println(uamount+" is Amount debited from your account remaining balance is : "+sub);
					}
					}
					else
					{
						System.out.println(uamount +" insufficent balance your current balance is : "+damount);
					}
					
					
				}
				else
				{
					System.out.println(" Enter the Valid Amount ");
				}
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changePassword() 
	{
		
		try 
		{
			connection = DriverManager.getConnection(url);
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void bankDetails() 
	{
			
			Scanner scan= new Scanner(System.in);
			System.out.println("Enter your mobile number ");
			String mb = scan.next();
			System.out.println("Enter your password ");
			String pin = scan.next();
			
			String select = "select * from bank where mobilenumber=? and pin=?";
			int holderId;
			String holderName;
			double holderAmount;
			String holderMobilenumber;
			
			try 
			{
				connection = DriverManager.getConnection(url);
				PreparedStatement ps2 = connection.prepareStatement(select);
				ps2.setString(1, mb);
				ps2.setString(2, pin);
				
				ResultSet res = ps2.executeQuery();
					while (res.next())
					{
						holderId=res.getInt(1);
						holderName=res.getString(2);
						holderAmount=res.getDouble(3);
						holderMobilenumber=res.getString(4);
						
						System.out.println("  TECA40 bank  ");
						System.out.println(" * * * * * * * * * * ");
						System.out.println("Your Bank Details ");
						System.out.println("holderId : "+holderId);
						System.out.println("holderName : "+holderName);
						System.out.println("holderAmount : "+holderAmount);
						System.out.print("holderMobiulenumber : ");
						for (int i = 0; i < holderMobilenumber.length(); i++)
						{
							if (i>2 && i<8) 
							{
								System.out.print(" *");
							}
							else
							{
								System.out.print(holderMobilenumber.charAt(i));
							}
						}
						System.out.println("\n * * * * * * * * * * ");
					}
		}  
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mobileTransaction() 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your mobile number");
		String mb =scan.next();
		System.out.println("Enter your pin");
		String pin = scan.next();
		
		String select = "select * from bank where mobilenumber=? and pin=?";
		double samount;
		String sname;
		String smb;
		try 
		{
			connection = DriverManager.getConnection(url);
			PreparedStatement ps6= connection.prepareStatement(select);
			ps6.setString(1, mb);
			ps6.setString(2, pin);
			
			ResultSet res = ps6.executeQuery();
			if (res.next()) 
			{
				samount=res.getDouble(3);
				sname=res.getString(2);
				smb=res.getString(4);
				System.out.println(" Enter receiver Mobile number ");
				String rmb= scan.next();
				String  select1 = "select * from bank where mobilenumber=?";
				PreparedStatement ps7= connection.prepareStatement(select1);
				ps7.setString(1, rmb);
				//ps7.setString(2, pin);
				ResultSet res1 = ps7.executeQuery();
				
				if (res1.next()) 
				{
					double rsamount = res1.getDouble(3);
					System.out.println("Enter Amount");
					double ramount = scan.nextDouble();
					
					if (samount>=ramount)
					{
						//sender 
						double sub =samount-ramount;
						String updates =" update bank set amount=? where mobilenumber=?";
						PreparedStatement pss =connection.prepareStatement(updates);
							pss.setDouble(1, sub);
							pss.setString(2, mb);
							
							int num = pss.executeUpdate();
							if (num!=0) 
							{
								System.out.println(ramount+"  Transaction successful  from "+ samount+"Amount debited availble balance is :  "+sub);
							
								//receiver
								double add = ramount+samount;
								String updater =" update bank set amount=? where mobilenumber=?";
								PreparedStatement psr = connection.prepareStatement(updater);
								psr.setDouble(1, add);
								psr.setString(2, rmb);
								int num1 = psr.executeUpdate();
								if (num1!=0) 
								{
										System.out.println(" Received Amount From "+sname);
										System.out.print(" mobilenumber : ");
										for (int i = 0; i < smb.length(); i++) 
										{
											if (i>2 && i<8)
											{
												System.out.print(" *");
											}
											else
											{
												System.out.print(smb.charAt(i));
											}
										}
										System.out.println();
										System.out.println("\n Amount of "+ramount+"/- credited to your account ");
										System.out.println(ramount+" Available balance is :  "+add);
								}
							}
							else
							{
								
							}
					}
					else
					{
						System.out.println(" failed ");	
					}
							
					
					
				}
			}
			else
			{
				System.out.println(" velli account open chy ra pumka ");
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
