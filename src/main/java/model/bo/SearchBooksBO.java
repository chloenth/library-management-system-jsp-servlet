package model.bo;

import java.util.ArrayList;

import model.bean.Book;
import model.dao.SearchBooksDAO;

public class SearchBooksBO {
	SearchBooksDAO searchBooksDAO = new SearchBooksDAO();

	public ArrayList<Book> getBooksBySearchAndPage(String searchText, String status, int pageNumber, int pageSize) {
		return searchBooksDAO.getBooksBySearchAndPage(searchText, status, pageNumber, pageSize);
		
	}

	public int calculatePagesBySearch(String searchText, String status, int pageSize) {
		double totalBooksBySearch = searchBooksDAO.getBookCountBySearch(searchText, status);
		int totalPages = 0;
		
		if(pageSize > 0) {
			totalPages = (int) Math.ceil(totalBooksBySearch / pageSize);
		}
		
		System.out.println("total books by search: " + totalBooksBySearch);
		System.out.println("total pages: " + totalPages);
		
		return totalPages;
	}

}
