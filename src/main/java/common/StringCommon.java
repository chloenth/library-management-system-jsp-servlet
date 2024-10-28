package common;

public class StringCommon {
	public static String convertNumberToString(long number, int digit) {
		// 16 số 0 : Hàm này chỉ có thể convert cho số nguyên có chiều dài tối đa là 16
		// chữ số
		number = number + 10000000000000000L;

		String returnedStr = String.valueOf(number); // có chiều dài là 17 chữ

		return returnedStr.substring(returnedStr.length() - digit);
	}

	// to check if a string is an integer
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
