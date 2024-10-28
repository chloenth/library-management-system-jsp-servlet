package model.bo;

import org.mindrot.jbcrypt.BCrypt;

import model.dao.RegisterDAO;
import common.ResultCode;
import common.StringCommon;
import common.ValidateCommon;

public class RegisterBO {
	RegisterDAO registerDAO = new RegisterDAO();

	// Register account
	public ResultCode registerAccount(String username, String fullname, String email, String password,
			String confirmPassword) {
		ResultCode result = ResultCode.UNKNOWN_ERROR;

		if (!ValidateCommon.checkRequiredFields(username, fullname, email, password)) {
			return ResultCode.REQUIRED_FIELDS_ERROR;
		}

		if (!ValidateCommon.checkEmailFormat(email)) {
			return ResultCode.INVALID_EMAIL_FORMAT;
		}

		if (!password.equals(confirmPassword)) {
			return ResultCode.PASSWORD_MISMATCH;
		}

		// Hash the password
		String hashedPassword = hashPassword(password);

		for (int i = 1; i <= 10; i++) {
			// get latest UID
			String latestUID = registerDAO.getLatestAccountID();
			System.out.println("latest UID: " + latestUID);

			// Tách UID và số thứ tự ra riêng
			// Tăng số thứ tự lên 1
			// Gộp số thứ tự mới với UID

			if (latestUID == null) {
				latestUID = "ACC00001";
			} else {
				long sequenceNumber = Long.valueOf(latestUID.substring(3));
				sequenceNumber++;
				latestUID = "ACC" + StringCommon.convertNumberToString(sequenceNumber, 5);
			}

			result = registerDAO.registerAccount(latestUID, username, fullname, email, hashedPassword);

			if (ResultCode.DUPLICATE_ID_ERROR.equals(result)) {
				continue;
			} else if (ResultCode.SUCCESS.equals(result)) {
				break;
			}

		}

		return result;
	}

	private String hashPassword(String password) {
		// Generate a salt
		String salt = BCrypt.gensalt(12); // 12 is the log rounds (work factor)

		return BCrypt.hashpw(password, salt);
	}

}
