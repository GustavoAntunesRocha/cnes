package br.com.gustavo.cnesAPI.exceptions;

public class ProfissionalNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProfissionalNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
