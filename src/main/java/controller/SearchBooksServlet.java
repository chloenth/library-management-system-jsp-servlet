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
import model.bo.SearchBooksBO;

/**
 * Servlet implementation class SearchBookServlet
 */
@WebServlet("/SearchBooksServlet")
public class SearchBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBooksServlet() {
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

		String page = request.getParameter("page");
		int pageNumber = 1;

		if (page != null && !("").equals(page)) {
			pageNumber = Integer.valueOf(page);
		}

		int pageRange = 5; // numbers of page to be displayed on screen
		int pageSize = 10; // numbers of books per page

		String searchText = request.getParameter("searchText");
		String status = request.getParameter("status");
		
		// check if there is searchText value stored in session attribute
		String sessionSearchText = (String) session.getAttribute("searchText");
		// check if there is status value stored in session attribute
		String sessionStatus = (String) session.getAttribute("status");

		if (searchText == null && sessionSearchText != null) {
			searchText = sessionSearchText;
		}
		
		if (status == null && sessionStatus != null) {
			status = sessionStatus;
		}

		SearchBooksBO searchBooksBO = new SearchBooksBO();
		ArrayList<Book> filteredBooksByPage = searchBooksBO.getBooksBySearchAndPage(searchText, status, pageNumber, pageSize);

		int totalPages = searchBooksBO.calculatePagesBySearch(searchText, status, pageSize);
		int[] pageBounds = PaginationCommon.calculatePageBounds(pageNumber, totalPages, pageRange);

		request.setAttribute("pagedBookList", filteredBooksByPage);
		request.setAttribute("currentPage", pageNumber);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("startPage", pageBounds[0]);
		request.setAttribute("endPage", pageBounds[1]);
		
		session.setAttribute("searchText", searchText);
		session.setAttribute("status", status);

		RequestDispatcher rd = request.getRequestDispatcher("bookList.jsp");
		rd.forward(request, response);
	}

}
