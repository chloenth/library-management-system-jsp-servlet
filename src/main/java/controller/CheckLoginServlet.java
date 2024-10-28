package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ResultCode;
import model.bo.CheckLoginBO;

/**
 * Servlet implementation class CheckLoginServlet
 */
@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();

		CheckLoginBO checkLoginBO = new CheckLoginBO();
		ResultCode result = checkLoginBO.validateCredentials(username, password);

		if (ResultCode.SUCCESS.equals(result)) {
			session.setAttribute("username", username);
			response.sendRedirect("dashboardAdmin.jsp");

		} else {
			response.sendRedirect("login.jsp?error=" + result.getCode());

		}
	}

}
