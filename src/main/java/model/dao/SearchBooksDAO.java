package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Book;

public class SearchBooksDAO extends BaseDAO {

	// Get books list by search and page
	public ArrayList<Book> getBooksBySearchAndPage(String searchText, String status, int pageNumber, int pageSize) {
		ArrayList<Book> filteredBooksByPage = new ArrayList<>();

		Connection connection = getConnection();

		StringBuilder sql = new StringBuilder("""
				SELECT *
				FROM (
					SELECT ROW_NUMBER() OVER (ORDER BY books.id) AS row_num,
					books.*, author_name
					FROM books
					INNER JOIN authors ON books.author_id = authors.id
					WHERE (title LIKE ? OR author_name LIKE ?)
				) AS tempTable
				WHERE
					row_num > (? * (? - 1))					-- Start for the current row
					AND row_num <= (? * (? - 1) + ?)		-- End for the current row
				""");

		ArrayList<String> parameterList = new ArrayList<>();
		parameterList.add("%" + searchText + "%"); // title LIKE ?
		parameterList.add("%" + searchText + "%"); // author_name LIKE ?

		// check if status should be filtered
		if (!"all".equals(status)) {
			int insertPosition = sql.indexOf(") AS tempTable");
			sql.insert(insertPosition, " AND status = ?");
			parameterList.add(status); // status = ?
		}

		// row_num > (? * (? - 1)) -- Start for the current row
		// AND row_num <= (? * (? - 1) + ?) -- End for the current row
		parameterList.add(String.valueOf(pageSize));
		parameterList.add(String.valueOf(pageNumber));
		parameterList.add(String.valueOf(pageSize));
		parameterList.add(String.valueOf(pageNumber));
		parameterList.add(String.valueOf(pageSize));

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(sql.toString());

			for (int i = 0, n = parameterList.size(); i < n; i++) {
				pstmt.setString(i + 1, parameterList.get(i));
			}

			rs = pstmt.executeQuery();

			Book book = null;

			while (rs.next()) {
				book = new Book();

				book.setId(rs.getString("id"));
				book.setRowNum(rs.getInt("row_num"));
				book.setTitle(rs.getString("title"));
				book.setAuthorName(rs.getString("author_name"));
				book.setGenre(rs.getString("genre"));
				book.setLanguage(rs.getString("language"));
				book.setDescription(rs.getString("description"));
				book.setStatus(rs.getString("status"));
				book.setRating(rs.getString("rating"));
				book.setPublicationYear(rs.getString("publication_year"));

				filteredBooksByPage.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return filteredBooksByPage;
	}

	// count total books by search text
	public double getBookCountBySearch(String searchText, String status) {
		double bookCount = 0;

		Connection connection = getConnection();

		StringBuilder sql = new StringBuilder("""
				SELECT COUNT(books.id) AS book_count
				FROM books
				INNER JOIN authors ON books.author_id = authors.id
				WHERE (title LIKE ? OR author_name LIKE ?)
				""");

		if (!"all".equals(status)) {
			sql.append(" AND status = ?");
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + searchText + "%");
			pstmt.setString(2, "%" + searchText + "%");

			if (!"all".equals(status)) {
				pstmt.setString(3, status);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("I am getting book count");
				bookCount = rs.getInt("book_count");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return bookCount;
	}

}
