package bankproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		String mb = req.getParameter("mb");
		System.out.println(mb);
		
		String pin = req.getParameter("pin");
		System.out.println(pin);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select = "select * from bank where mobilenumber=? and pin=?";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mb);
			ps.setString(2, pin);
			
			ResultSet res = ps.executeQuery();
			if (res.next())
			{
				double damount = res.getDouble("amount");
				RequestDispatcher rd = req.getRequestDispatcher("FriendsBank.html");
				rd.include(req, resp);
				writer.println("<center><h1> your current Account Balance is : "+ damount +"</h1></center>");
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("CheckBalanace.html");
				rd.include(req, resp);
				writer.println("<center><h1> invalid details re-enter </h1></center>");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
