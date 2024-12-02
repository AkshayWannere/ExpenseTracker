package in.scarface.expensetraackerapi.ExceptionHandling;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979461382919655877L;

	public ResourceNotFoundException(String message) {
	super(message);
	}
}
