package bankproject;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CDAmount")
public class CDAmount  extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String uamount1=req.getParameter("amount");
		double uamount = Double.parseDouble(uamount1);
		System.out.println(uamount);
		
		HttpSession session = req.getSession();
		double damount = (double) session.getAttribute("damount");
		String mb =	(String) session.getAttribute("MB");
		String pin = (String) session.getAttribute("PIN");
		String url =(String) session.getAttribute("URL");
		//System.out.println(url);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String update = "update bank set amount =? where mobilenumber=?";

		
		if (uamount>0)
		{
			try 
			{
				double add = damount+uamount;
				
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(update);
				ps.setDouble(1, add);
				ps.setString(2, mb);
				
				int num = ps.executeUpdate();
				if (num!=0)
				{
					RequestDispatcher rd = req.getRequestDispatcher("FriendsBank.html");
					rd.include(req, resp);
					writer.println("<center><h1>"+uamount+" is Amount credited to your account available balance is : "+add+"</h1></center>");
					System.out.println(uamount+" is Amount credited to your account available balance is : "+add);
				}
				else
				{
					System.out.println(" 404 error --means network error");
				}
				
		
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("CDamount.html");
			rd.include(req, resp);
			writer.println("<center><h1> Enter the Valid Amount  </h1></center>");
			System.out.println(" Enter the Valid Amount ");

		}
	}
}
