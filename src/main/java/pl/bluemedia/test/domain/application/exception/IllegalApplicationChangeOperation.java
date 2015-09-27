package pl.bluemedia.test.domain.application.exception;

public class IllegalApplicationChangeOperation extends RuntimeException {
	private static final long serialVersionUID = -7140124856439528592L;
	
	public IllegalApplicationChangeOperation() {
		this("Operation can't be applied on the application in its current state.");
	}
	
	public IllegalApplicationChangeOperation(String message) {
		super(message);
	}
}
