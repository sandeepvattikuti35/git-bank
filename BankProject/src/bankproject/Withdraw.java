package bankproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet
{
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
		{
			String mb =	req.getParameter("mb");
			String password = req.getParameter("password");
			
			
			PrintWriter writer = resp.getWriter();
			resp.setContentType("text/html");
			
			HttpSession session = req.getSession();
			
			String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
			String select = " select * from bank where mobilenumber=? and pin=?";
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(select);
				ps.setString(1, mb);
				ps.setString(2, password);
				
				ResultSet res = ps.executeQuery();
				if (res.next())
				{
					Random r = new Random();
					int  otp=r.nextInt(10000);
					if (otp<1000)
					{
						otp=otp+1000;
					}
					
					double damount = res.getDouble("amount");
					session .setAttribute("damount", damount);
					session.setAttribute("otp", otp);
					session.setAttribute("mb", mb);
					session.setAttribute("pin", password);
					
					session.setMaxInactiveInterval(15);
					writer.println("<center><h1> "+otp+"  </h1></center>");
					RequestDispatcher rd = req.getRequestDispatcher("WithdrawOTP.html");
					rd.include(req, resp);
					writer.println("<center><h1> ok </h1></center>");
				}
				
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
					rd.include(req, resp);
					writer.println("<center><h1> In valid details</h1></center>");
				}
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
}
