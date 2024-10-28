package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.PaginationCommon;
import model.bean.Book;
import model.bo.ShowBookListBO;

/**
 * Servlet implementation class ShowBookListServlet
 */
@WebServlet("/ShowBookListServlet")
public class ShowBookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowBookListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		if (session.getAttribute("username") == null) {
			response.sendRedirect("login.jsp?error= Please log in");
			return;
		}

		// clear session attribute searchText
		if (session.getAttribute("searchText") != null) {
			session.removeAttribute("searchText");
		}

		// clear session attribute status
		if (session.getAttribute("status") != null) {
			session.removeAttribute("status");
		}

		String page = request.getParameter("page");
		int pageNumber = 1;

		if (page != null && !("").equals(page)) {
			pageNumber = Integer.valueOf(page);
		}

		int pageRange = 5; // numbers of page to be displayed on screen
		int pageSize = 10; // numbers of books per page

		ShowBookListBO showBookListBO = new ShowBookListBO();

		ArrayList<Book> pagedBookList = showBookListBO.getPagedBookList(pageNumber, pageSize);
		int totalPages = showBookListBO.calculateTotalPages(pageSize);

		int[] pageBounds = PaginationCommon.calculatePageBounds(pageNumber, totalPages, pageRange);

		RequestDispatcher rd = request.getRequestDispatcher("bookList.jsp");

		request.setAttribute("pagedBookList", pagedBookList);
		request.setAttribute("currentPage", pageNumber);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("startPage", pageBounds[0]);
		request.setAttribute("endPage", pageBounds[1]);

		rd.forward(request, response);

	}

}
