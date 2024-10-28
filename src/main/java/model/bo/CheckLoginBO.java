package model.bo;

import org.mindrot.jbcrypt.BCrypt;

import common.ResultCode;
import common.ValidateCommon;
import model.dao.CheckLoginDAO;

public class CheckLoginBO {
	CheckLoginDAO checkLoginDAO = new CheckLoginDAO();

	public ResultCode validateCredentials(String username, String password) {

		if (!ValidateCommon.checkRequiredFields(username, password)) {
			return ResultCode.REQUIRED_FIELDS_ERROR;
		}

		String passwordHash = checkLoginDAO.getPasswordHash(username);

		if (passwordHash == null || !BCrypt.checkpw(password, passwordHash)) {
			return ResultCode.AUTH_INVALID_CREDENTIALS;

		}

		return ResultCode.SUCCESS;
	}

}
