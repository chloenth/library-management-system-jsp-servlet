package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.ResultCode;

public class RegisterDAO extends BaseDAO {
	
	// Register account
	public ResultCode registerAccount(String uid, String username, String fullname, String email, String hashedPassword) {
		ResultCode result = ResultCode.UNKNOWN_ERROR;

		Connection connection = getConnection();
		String sql = "INSERT INTO accounts VALUES (?, ?, ?, ?, ?, 'ADMIN')";

		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, uid);
			pstmt.setString(2, username);
			pstmt.setString(3, fullname);
			pstmt.setString(4, email);
			pstmt.setString(5, hashedPassword);

			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				result = ResultCode.SUCCESS;
				
			}

		} catch (SQLException e) {
			e.printStackTrace();

			String errorMessage = e.getMessage();
			if (errorMessage != null && errorMessage.contains("The duplicate key value is")) {
				result = ResultCode.DUPLICATE_ID_ERROR;
			}

		} finally {
			closeConnection(connection, pstmt, null);

		}
		

		return result;
	}

	// Get latest ID
	public String getLatestAccountID() {
		return getLatestID("accounts");
	}

}
