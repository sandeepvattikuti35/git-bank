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
import javax.servlet.http.HttpSession;

@WebServlet("/Credit")
public class Credit  extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String mb = req.getParameter("mb");
		System.out.println(mb);
		
		String pin = req.getParameter("pin");
		System.out.println(pin);
		
		HttpSession session = req.getSession();
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select = "select * from bank where mobilenumber=? and pin=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection  connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mb);
			ps.setString(2, pin);
			
			ResultSet res = ps.executeQuery();
			if (res.next())
			{	
				double damount = res.getDouble("amount");
				session.setAttribute("damount", damount);
				session.setAttribute("MB", mb);
				session.setAttribute("PIN", pin);
				session.setAttribute("URL", url);
				RequestDispatcher rd = req.getRequestDispatcher("CDamount.html");
				rd.forward(req, resp);
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("Credit.html");
				rd.include(req, resp);
				writer.println("<center><h1> Invalid details pls re-enter </h1></center>");
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
