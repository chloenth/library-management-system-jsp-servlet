package common;

public class ValidateCommon {
	public static boolean checkRequiredFields(String... strings) {
		String[] strs = strings;

		for (int i = 0, n = strs.length; i < n; i++) {
			if (strs[i] == null || "".equals(strs[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean checkEmailFormat(String email) {

		String emailPattern = "^[a-zA-Z0-9+_%-]+(\\.[a-zA-Z0-9+_%-]+)*@[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$";

		if (email.matches(emailPattern)) {
			return true;
		}

		return false;
	}

}
