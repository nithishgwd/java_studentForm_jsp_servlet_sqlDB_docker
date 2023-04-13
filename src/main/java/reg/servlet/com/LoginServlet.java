package reg.servlet.com;

import cc221047018.JDBCConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public LoginServlet(){
        super();
    }
      
	static Logger log = Logger.getLogger(LoginServlet.class.getName());
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String username = request.getParameter("uname");
		String password = request.getParameter("pass");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCConnection.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM adminLogin WHERE uname = ? AND password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				log.info("User " + username + " logged in successfully.");
				HttpSession session = request.getSession();
				//session.setAttribute("username", username);
				session.setAttribute("loggedIn", true);
				response.sendRedirect("register.jsp");
			} else {
				log.error("Invalid username or password.");
				request.setAttribute("errorMessage", "Invalid username or password");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.error("Error: " + e.getMessage());
			throw new ServletException("Database error", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}