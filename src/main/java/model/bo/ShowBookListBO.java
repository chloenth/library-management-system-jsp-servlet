package model.bo;

import java.util.ArrayList;

import model.bean.Book;
import model.dao.ShowBookListDAO;

public class ShowBookListBO {
	ShowBookListDAO showBookListDAO = new ShowBookListDAO();

	public ArrayList<Book> getPagedBookList(int pageNumber, int pageSize) {
		return showBookListDAO.getPagedBookList(pageNumber, pageSize);

	}

	public int calculateTotalPages(int pageSize) {
		double totalBookCount = showBookListDAO.getTotalBookCount();
		int totalPages = 0;

		if (pageSize > 0) {
			totalPages = (int) Math.ceil(totalBookCount / pageSize);
		}

		return totalPages;

	}

}
