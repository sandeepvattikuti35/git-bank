package bankproject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/WithdrawOTPpage")
public class WithdrawOTPpage  extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String uotp = req.getParameter("OTP");
		int uotp1 = Integer.parseInt(uotp);
		System.out.println(uotp1);
		
		HttpSession session=req.getSession();
		Integer otp = (Integer) session.getAttribute("otp");
		System.out.println(otp);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
	
		if (otp!=null) //time session condition
		{
				if (uotp1==otp)//checking otp condition
				{
							writer.println("<center><h1> go to amount page</h1></center> ");
							RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
							rd.forward(req, resp);
							writer.println("<center><h1>  amount </h1></center> ");
				} 
				else
				{
						writer.println("<center><h1> "+ otp +" </h1></center>");
						RequestDispatcher rd = req.getRequestDispatcher("WithdrawOTP.html");
						rd.include(req, resp);
						writer.println("<center><h1> IN valid OTP </h1></center>");

				}
			
		} 
		else 
		{
			
			RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
			rd.include(req, resp);
			writer.println("<center><h1> session time out </h1></center>");
		}
		
	}
}
