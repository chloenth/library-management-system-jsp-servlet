package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Book;

public class ShowBookListDAO extends BaseDAO {

	// Get BookList by page number
	public ArrayList<Book> getPagedBookList(int pageNumber, int pageSize) {
		ArrayList<Book> pagedBooksList = new ArrayList<>();

		Connection connection = getConnection();

		String sql = """
				SELECT *
				FROM (
					SELECT
						ROW_NUMBER() OVER (ORDER BY books.id) AS row_num,
						books.*, author_name
					FROM books
					INNER JOIN authors ON books.author_id = authors.id
				) AS tempTable
				WHERE
					row_num > (? * (? - 1)) 				-- Start for the current row
					AND row_num <= (? * (? - 1) + ?)		-- End for current row
				ORDER BY row_num
				""";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, String.valueOf(pageSize));
			pstmt.setString(2, String.valueOf(pageNumber));
			pstmt.setString(3, String.valueOf(pageSize));
			pstmt.setString(4, String.valueOf(pageNumber));
			pstmt.setString(5, String.valueOf(pageSize));

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

				pagedBooksList.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return pagedBooksList;
	}

	// Get total page number in book list
	public double getTotalBookCount() {
		double totalBookCount = 0;

		Connection connection = getConnection();
		String sql = "SELECT COUNT(id) AS total_books FROM books";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalBookCount = rs.getInt("total_books");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ArithmeticException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return totalBookCount;
	}

}
