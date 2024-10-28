package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {

	/**
	 *
	 * 
	 * 
	 * get connection to database
	 *
	 * 
	 * 
	 * @return Connection to database if connect success
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException
	 * 
	 */

	// Get Connection
	public Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");

			String connectionURL = "jdbc:jtds:sqlserver://" + DatabaseConfig.HOSTNAME + ":1433/"
					+ DatabaseConfig.DATABASE + ";CharacterSet=UTF-8";

			conn = DriverManager.getConnection(connectionURL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);

			System.out.println("Connected!");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return conn;
	}

	// Close Connection
	public void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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
			e.printStackTrace();

		}

	}

	// Get Latest ID
	public String getLatestID(String tableName) {
		Connection connection = getConnection();
		String sql = "SELECT TOP 1 id FROM ? ORDER BY id DESC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String latestID = null;

		try {
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, tableName);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				latestID = rs.getString("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return latestID;
	}
}
