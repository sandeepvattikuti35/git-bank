 package bankproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;

@WebServlet("/MBAmount")
public class MBAmount extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		
		String ramount = req.getParameter("ramount");
		double renteramount=Double.parseDouble(ramount);
		System.out.println(renteramount);
		
		HttpSession session= req.getSession();
		double sdamount = (double) session.getAttribute("SAMOUNT");
		System.out.println(sdamount);
		String sname = (String) session.getAttribute("SNAME");
		String smb = (String) session.getAttribute("MB");
		String spin= (String) session.getAttribute("PIN");
		String url1=(String) session.getAttribute("URL");
		System.out.println(url1);
		
		String rmb = (String) session.getAttribute("RMB");
		double rdamount =(Double) session.getAttribute("RDAMOUNT");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		if (renteramount>0)
		{
				
				if (sdamount>=renteramount)
				{
					double sub = sdamount-renteramount;
					double add= rdamount+renteramount;
					
					//String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
					String updates="update bank set amount=? where mobilenumber=?";
					String updater="update bank set amount=? where mobilenumber=?";
					
					try 
					{
						//sender
						Connection connection =	DriverManager.getConnection(url1);
						PreparedStatement pss = connection.prepareStatement(updates);
						pss.setDouble(1, sub);
						pss.setString(2, smb);
						
						int num = pss.executeUpdate();
						if (num > 0)
						{
								
								//receiver
								PreparedStatement psr = connection.prepareStatement(updater);
								psr.setDouble(1, add);
								psr.setString(2, rmb);
								int num1 = psr.executeUpdate();
								if (num1 > 0)
								{
									RequestDispatcher rd = req.getRequestDispatcher("FriendsBank.html");
									rd.include(req, resp);
									writer.println("<center><h1> Amount Received successful..</h1></center>");
									
									writer.println("<center><h1> Received Amount From "+sname +"</h1></center>");
									writer.print("<center><strong> Mobile number : </strong></center>");
									
									System.out.println(" Received Amount From "+sname);
									System.out.print(" mobilenumber : ");
									for (int i = 0; i < smb.length(); i++) 
										{
											if (i>2 && i<8)
											{
												writer.print("<center1><strong style=color:blue> * </strong></center>");
												System.out.print(" * ");
											}
											else
											{
												writer.print("<center1><strong style=color:lime>"+ smb.charAt(i)+" </strong></center>");
												System.out.print(smb.charAt(i));
											}
										}
										writer.println();
										writer.println("<center1><h1 style=color:green> \n Amount of "+ renteramount+" /- credited to your account </h1></center>");
										writer.println("<center1><h1 style=color:green> "+ renteramount +" Available balance is  :"+ add+" </h1></center>");
		
										System.out.println();
										System.out.println("\n Amount of "+renteramount+"/- credited to your account ");
										System.out.println(renteramount+" Available balance is :  "+add);
		
										
								}
								else 
								{
									writer.println("<center><h1 style=color:green>"+ renteramount+" Transaction successful  from "+ sdamount+"Amount debited availble balance is :  "+sub+"</h1></center>");
									System.out.println(renteramount+"  Transaction successful  from  "+ sdamount+"Amount debited availble balance is :  "+sub);								
									
//									RequestDispatcher rd = req.getRequestDispatcher("FriendsBank.html");
//									rd.include(req, resp);
//									writer.println("<center><h1> Transaction successful </h1></center>");
//									
								}
							
							RequestDispatcher rd = req.getRequestDispatcher("FriendsBank.html");
							rd.include(req, resp);
							writer.println("<center><h1> </h1></center>");
											
						}
						else 
						{

							RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
							rd.include(req, resp);
							writer.println("<center><h1> Error..</h1></center>");
							
						}
					} 
					catch (SQLException e)
					{
						e.printStackTrace();
					}

					
				}
				else 
				{
					RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
					rd.include(req, resp);
					writer.println("<center><h1> Insufficent balance </h1></center>");
				
				}
			
		} 
		else 
		{
			RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
			rd.include(req, resp);
			writer.println("<center><h1> Invalid details</h1></center>");
		}
		
		
		
//		try
//		{
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection =	DriverManager.getConnection(url);
//			PreparedStatement ps = connection.prepareStatement(select);
//			ps.setString(1, reRmb);
//			
//			ResultSet res = ps.executeQuery();
//			
//			if (res.next()) 
//			{
//			
//				if (sdamount>=renteramount) 
//				{
//					//sender
//		
//				double sub =sdamount-renteramount;
//				String updates =" update bank set amount=? where mobilenumber=?";
//				PreparedStatement pss =connection.prepareStatement(updates);
//					pss.setDouble(1, sub);
//					pss.setString(2, smb);
//					
//					int num = pss.executeUpdate();
//					if (num!=0) 
//					{
//						writer.println("<center><h1 style=color:green>"+ ramount+" Transaction successful  from "+ sdamount+"Amount debited availble balance is :  "+sub+"</h1></center>");
//						System.out.println(ramount+"  Transaction successful  from  "+ sdamount+"Amount debited availble balance is :  "+sub);								
//						//receiver
//						double add = rdamount+renteramount;
//						String updater =" update bank set amount=? where mobilenumber=?";
//						PreparedStatement psr = connection.prepareStatement(updater);
//						psr.setDouble(1, add);
//						psr.setString(2, rmb);
//						int num1 = psr.executeUpdate();
//						if (num1!=0) 
//						{
//								writer.println("<center><h1> Received Amount From "+sname +"</h1></center>");
//								writer.print("<center><strong> Mobile number : </strong></center>");
//								System.out.println(" Received Amount From "+sname);
//								System.out.print(" mobilenumber : ");
//								for (int i = 0; i < smb.length(); i++) 
//								{
//									if (i>2 && i<8)
//									{
//										writer.print("<center1><strong style=color:blue> * </strong></center>");
//										System.out.print(" *");
//									}
//									else
//									{
//										writer.print("<center1><strong style=color:lime>"+ smb.charAt(i)+" </strong></center>");
//										System.out.print(smb.charAt(i));
//									}
//								}
//								writer.println();
//								writer.println("<center1><h1 style=color:green> \n Amount of "+ renteramount+" /- credited to your account </h1></center>");
//								writer.println("<center1><h1 style=color:green> "+ renteramount +" Available balance is  :"+ add+" </h1></center>");
//
//								System.out.println();
//								System.out.println("\n Amount of "+renteramount+"/- credited to your account ");
//								System.out.println(renteramount+" Available balance is :  "+add);
//						}
//					}
//					else
//					{
//						RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
//						rd.include(req, resp);
//						writer.println("<center><h1 style=color:red> Incorrect details pls re-enter the details</h1></center>");
//					
//					}
//					
//				}
//				else
//				{
//					RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
//					rd.include(req, resp);
//					writer.println("<center><h1 style=color:red> Insufficent funds </h1></center>");
//				
//				System.out.println(" failed ");	
//				}	
//			}
//			else
//			{
//				RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
//				rd.include(req, resp);
//				writer.println("<center><h1 style=color:red> Incorrect details pls re-enter the details</h1></center>");
//			}
//		}
//		catch (Exception e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//

		
	}
}
