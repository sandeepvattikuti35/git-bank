package bankproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReceiverMB")
public class ReceiverMB extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

			String rmb = req.getParameter("rmb");
			System.out.println(rmb);
			
			HttpSession session = req.getSession();
			
//			String ramount = req.getParameter("ramount");
//			double renteramount=Double.parseDouble(ramount);
//			System.out.println(renteramount);
//			
			
//			HttpSession session= req.getSession();
//			String samount = (String) session.getAttribute("SAMOUNT");
//			double sdamount= Double.parseDouble(samount);
//			String sname = (String) session.getAttribute("SNAME");
//			String smb = (String) session.getAttribute("MB");
//			String spin= (String) session.getAttribute("PIN");
//			
			String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
			String select = "select * from bank where mobilenumber=? ";
			
			PrintWriter writer = resp.getWriter();
			resp.setContentType("text/html");
			
			Double rdamount;
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =	DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(select);
				ps.setString(1, rmb);
				
				ResultSet res = ps.executeQuery();
				
				if (res.next()) 
				{
					rdamount = res.getDouble(3);
					session.setAttribute("RMB", rmb);
					session.setAttribute("RDAMOUNT", rdamount);
					session.setAttribute("URL", url);
					RequestDispatcher rd = req.getRequestDispatcher("MbAmount.html");
					rd.forward(req, resp);
					
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("ReceiverMobileNumber.html");
					rd.include(req, resp);
					writer.println("<center><h1 style=color:red> Invalid mobile pls enter correct one</h1></center>");
				}
				
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
