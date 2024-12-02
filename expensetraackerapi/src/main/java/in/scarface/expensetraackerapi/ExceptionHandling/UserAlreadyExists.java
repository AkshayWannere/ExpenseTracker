package in.scarface.expensetraackerapi.ExceptionHandling;

public class UserAlreadyExists extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -725760925044563606L;

	public UserAlreadyExists(String message) {
		super(message);
	}
	
}
