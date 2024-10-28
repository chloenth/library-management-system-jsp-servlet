package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckLoginDAO extends BaseDAO {

	// Get password hash
	public String getPasswordHash(String username) {
		String passwordHash = null;

		Connection connection = getConnection();
		String sql = "SELECT password_hash FROM accounts WHERE user_name = ?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				passwordHash = rs.getString("password_hash");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(connection, pstmt, rs);

		}

		return passwordHash;
	}

}
