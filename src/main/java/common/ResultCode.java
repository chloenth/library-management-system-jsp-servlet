package common;

public enum ResultCode {
	SUCCESS(0, "Success"),
	DUPLICATE_ID_ERROR(1, "An internal error occurred, please try again later."),
	REQUIRED_FIELDS_ERROR(2, "Please fill in all required fields."),
	PASSWORD_MISMATCH(3, "The passwords do not match. Please try again."),
	INVALID_EMAIL_FORMAT(4, "The email format is invalid. Please enter a valid email address (e.g., name@example.com)."),
	AUTH_INVALID_CREDENTIALS(5, "Invalid username or password."),
	UNKNOWN_ERROR(6, "An internal error occurred, please try again later.");
	
	private final int code;
    private final String message;
    
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
    public static String getMessageByCode(int code) {
    	for(ResultCode resultCode: ResultCode.values()) {
    		if(resultCode.getCode() == code) {
    			return resultCode.getMessage();
    		}
    	}
    	
    	return ResultCode.UNKNOWN_ERROR.getMessage();
    }
}
